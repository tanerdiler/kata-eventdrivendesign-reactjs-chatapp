package com.tanerdiler.edd.event;


import jeventbus.streaming.EventContext;
import jeventbus.streaming.UnknownEnumTypeException;

import java.util.Optional;

public enum EDDEventContext implements EventContext {
    AUTH, MESSAGING;

    @Override
    public Object fromName(String s) {
        return null;
    }

    @Override
    public Optional parse(String name) throws UnknownEnumTypeException {
        return Optional.empty();
    }
}
