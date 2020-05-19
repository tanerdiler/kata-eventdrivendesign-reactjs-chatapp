package com.tanerdiler.edd.event.listener;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

public interface ChatRoomCreatedListener extends EventListener {

    void onChatRoomCreated(EventSource source);
}
