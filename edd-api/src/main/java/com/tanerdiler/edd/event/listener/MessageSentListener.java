package com.tanerdiler.edd.event.listener;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

public interface MessageSentListener extends EventListener {

    void onMessageSent(EventSource source);
}
