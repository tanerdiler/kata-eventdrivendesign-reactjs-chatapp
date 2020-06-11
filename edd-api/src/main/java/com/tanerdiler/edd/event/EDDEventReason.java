package com.tanerdiler.edd.event;

import jeventbus.streaming.EventReason;

public enum EDDEventReason implements EventReason {
    BAD_WORD;

    @Override
    public EDDEventReason fromName(String name) {
        return EDDEventReason.valueOf(name);
    }

}
