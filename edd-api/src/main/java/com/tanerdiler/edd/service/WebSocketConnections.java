package com.tanerdiler.edd.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

@Component
public class WebSocketConnections {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketConnections.class);

    private Set<WebSocketSession> sessions = new HashSet<>();

    private ClosedConnectionCleaner cleaner = new ClosedConnectionCleaner();

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private ObjectMapper jsonMapper;

    public WebSocketConnections(ObjectMapper jsonMapper) {
        cleaner.start();
        this.jsonMapper = jsonMapper;
    }

    public void add(WebSocketSession session) {
        sessions.add(session);
    }

    public void sendAll(Object data) {
        lock.readLock().lock();
        try{
            sessions.parallelStream().filter(s->{
                boolean open = s.isOpen();
                if (!open) {
                    cleaner.remove(s);
                }
                return open;
            }).forEach(send(data));
        } finally {
            lock.readLock().unlock();
        }
    }

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

    private class ClosedConnectionCleaner {

        private ScheduledExecutorService closedSessionCleaner = Executors.newSingleThreadScheduledExecutor();

        private Set<WebSocketSession> closedSession = new HashSet<>();

        public void start() {
            closedSessionCleaner.scheduleAtFixedRate(clean(), 30, 30, TimeUnit.SECONDS);
        }

        private Runnable clean() {
            return () -> {
                lock.writeLock().lock();
                try{
                    sessions.removeAll(closedSession);
                    closedSession.clear();
                } finally {
                    lock.writeLock().unlock();
                }
            };
        }

        public void remove(WebSocketSession session) {
            closedSession.add(session);
        }
    }
}
