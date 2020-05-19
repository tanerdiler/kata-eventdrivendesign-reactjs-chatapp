package com.tanerdiler.edd.event.listener;

import jeventbus.core.Listener;
import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

public interface WebSocketEstablishedListener extends EventListener {

    void onWebSocketEstablished(EventSource source);
}
