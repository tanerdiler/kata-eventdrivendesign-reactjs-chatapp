package com.tanerdiler.edd.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanerdiler.edd.event.EventDefinitions;
import com.tanerdiler.edd.event.listener.*;
import com.tanerdiler.edd.user.OnlineUsers;
import com.tanerdiler.edd.websocket.WebSocket;
import jeventbus.shared.EventSource;
import jeventbus.streaming.EventDefinition;
import jeventbus.streaming.EventMessage;
import jeventbus.streaming.EventToMessageConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class UINotifier implements UserSignedInListener, ChatRoomCreatedListener, MessageSentListener, MessageReadListener, MessageDeniedListener {

    private final OnlineUsers users;

    private final ObjectMapper mapper;

    private void notify(EventSource source) {

        String actor = (String) source.get("actor");
        EventDefinition definition = EventDefinitions.get(source.eventType());
        EventMessage message = EventToMessageConverter.convert(source.eventType(),source, definition);

        users.get().parallelStream().forEach(u-> {
            if (u.isOnline()) {
                try {
                    u.notify(mapper.writeValueAsString(message));
                } catch (WebSocket.WebSocketException | JsonProcessingException e) {
                    log.error(format("Exception on notifying user{%s}", u.getUsername()), e);
                }
            }
        });
    }

    @Override
    public void onChatRoomCreated(EventSource source) {
        notify(source);
    }

    @Override
    public void onMessageSent(EventSource source) {
        notify(source);
    }

    @Override
    public void onUserSignedIn(EventSource source) {
        notify(source);
    }

    @Override
    public void onMessageDenied(EventSource source) {
        notify(source);
    }

    @Override
    public void onMessageRead(EventSource source) {
        notify(source);
    }
}
