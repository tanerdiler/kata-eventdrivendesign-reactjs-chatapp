package com.tanerdiler.edd.event;

import jeventbus.shared.EventType;
import jeventbus.streaming.EventDefinition;

import java.util.HashMap;
import java.util.Map;

import static com.tanerdiler.edd.event.EDDEventContext.AUTH;
import static com.tanerdiler.edd.event.EDDEventContext.MESSAGING;
import static com.tanerdiler.edd.event.EDDEventType.*;

public class EventDefinitions {

    private static Map<EDDEventType, EventDefinition> map = new HashMap();

    static {
        map.put(MESSAGE_READ, new EDDEventDefinition(6, MESSAGING, true, MESSAGE_READ));
        map.put(MESSAGE_DENIED, new EDDEventDefinition(5, MESSAGING, true, MESSAGE_DENIED));
        map.put(MESSAGE_SENT, new EDDEventDefinition(1, MESSAGING, true, MESSAGE_SENT));
        map.put(USER_SIGNEDIN, new EDDEventDefinition(2, AUTH, true, USER_SIGNEDIN));
        map.put(WEBSOCKET_ESTABLISHED, new EDDEventDefinition(3, MESSAGING, false, WEBSOCKET_ESTABLISHED));
        map.put(CHAT_ROOM_CREATED, new EDDEventDefinition(4, MESSAGING, true, CHAT_ROOM_CREATED));
    }

    public static EventDefinition get(EventType eventType) {
        return map.get(eventType);
    }
}
