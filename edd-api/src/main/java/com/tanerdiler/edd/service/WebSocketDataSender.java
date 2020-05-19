package com.tanerdiler.edd.service;

import org.springframework.stereotype.Component;

@Component
public class WebSocketDataSender {

    private WebSocketConnections connections;

    private WebSocketDataSender(WebSocketConnections connections) {

        this.connections = connections;
    }

    public void publish(Object data){
        connections.sendAll(data);
    }
}
