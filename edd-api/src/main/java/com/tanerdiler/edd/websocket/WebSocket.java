package com.tanerdiler.edd.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class WebSocket {

    private final WebSocketSession session;

    public void sendMessage(String message) throws WebSocketException {
        if (isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                throw new WebSocketException("Exception on sending message via session", e);
            }
        }
    }

    public boolean isOpen() {
        return nonNull(session) && session.isOpen();
    }

    public static class WebSocketException extends Exception {
        public WebSocketException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }
}
