package com.tanerdiler.edd.event.listener;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

public interface MessageDeniedListener extends EventListener {

    void onMessageDenied(EventSource source);
}
