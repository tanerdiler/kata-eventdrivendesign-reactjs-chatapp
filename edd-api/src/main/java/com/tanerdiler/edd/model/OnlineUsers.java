package com.tanerdiler.edd.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanerdiler.edd.event.listener.UserSignedInListener;
import com.tanerdiler.edd.event.listener.WebSocketEstablishedListener;
import com.tanerdiler.edd.service.WebSocketConnections;
import jeventbus.shared.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

@Component
public class OnlineUsers implements UserSignedInListener, WebSocketEstablishedListener {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketConnections.class);

    private Map<String, User> users = new HashMap<>();

    private ClosedConnectionCleaner cleaner = new ClosedConnectionCleaner();

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private ObjectMapper jsonMapper;

    public OnlineUsers(ObjectMapper jsonMapper) {
        cleaner.start();
        this.jsonMapper = jsonMapper;
    }

    @Override
    public void onUserSignedIn(EventSource source) {
        String username = (String) source.get("username");
        User user = new User(username);
        users.put(username, user);
    }

    @Override
    public void onWebSocketEstablished(EventSource source) {
        String username = (String) source.get("username");
        WebSocketSession webSocket = (WebSocketSession) source.get("websocket");
        Optional.ofNullable(users.get(username)).ifPresent(u->u.setWebSocket(new WebSocket(webSocket)));
    }
//
//    public void sendAll(Object data) {
//        lock.readLock().lock();
//        try{
//            users.values().parallelStream().filter(s->{
//                boolean open = s.isOnline();
//                if (!open) {
//                    cleaner.remove(s);
//                }
//                return open;
//            }).forEach(send(data));
//        } finally {
//            lock.readLock().unlock();
//        }
//    }

    public Consumer<WebSocketSession> send(Object data) {
        return (s) -> {
            try {
                String dataJson = jsonMapper.writeValueAsString(data);
                s.sendMessage(new TextMessage(dataJson));
            }
            catch (java.io.IOException e) {
                LOG.error("Sending metric data failed!", e);
            }
        };
    }

    public Collection<User> get() {
        return users.values();
    }

    private class ClosedConnectionCleaner {

        private ScheduledExecutorService closedSessionCleaner = Executors.newSingleThreadScheduledExecutor();

        private Set<User> closedSession = new HashSet<>();

        public void start() {
            closedSessionCleaner.scheduleAtFixedRate(clean(), 30, 30, TimeUnit.SECONDS);
        }

        private Runnable clean() {
            return () -> {
                lock.writeLock().lock();
                try{
                    closedSession.forEach(u->users.remove(u.getUsername()));
                    closedSession.clear();
                } finally {
                    lock.writeLock().unlock();
                }
            };
        }

        public void remove(User user) {
            closedSession.add(user);
        }
    }
}
