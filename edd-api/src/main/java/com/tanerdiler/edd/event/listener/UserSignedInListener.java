package com.tanerdiler.edd.event.listener;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

public interface UserSignedInListener extends EventListener {

    void onUserSignedIn(EventSource source);
}
