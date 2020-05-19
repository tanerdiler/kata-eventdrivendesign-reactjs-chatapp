package com.tanerdiler.edd.event;

import jeventbus.shared.EventType;

public enum EDDEventType implements EventType {
    USER_SIGNEDIN("onUserSignedIn"),
    WEBSOCKET_ESTABLISHED("onWebSocketEstablished"),
    MESSAGE_READ("onMessageRead"),
    MESSAGE_SENT("onMessageSent"),
    MESSAGE_DENIED("onMessageDenied"),
    CHAT_ROOM_CREATED("onChatRoomCreated");

    private String methodName;

    EDDEventType(String methodName) {

        this.methodName = methodName;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }
}
