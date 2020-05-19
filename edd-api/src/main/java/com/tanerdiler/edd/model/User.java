package com.tanerdiler.edd.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jeventbus.shared.EventSource;
import jeventbus.streaming.EventMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Objects.nonNull;

@Getter
@Slf4j
public class User {
    @Autowired
    private ObjectMapper objectMapper;

    private String username;

    private WebSocket webSocket;

    public User(String username) {

        this.username = username;
    }

    public boolean isOnline() {
        return nonNull(webSocket) && webSocket.isOpen();
    }

    public void setWebSocket(WebSocket webSocket) {

        this.webSocket = webSocket;
    }

    public void notify(String message) throws WebSocket.WebSocketException {
        webSocket.sendMessage(message);
    }
}
