package com.tanerdiler.edd.event.listener;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

public interface MessageReadListener extends EventListener {

    void onMessageRead(EventSource source);
}
