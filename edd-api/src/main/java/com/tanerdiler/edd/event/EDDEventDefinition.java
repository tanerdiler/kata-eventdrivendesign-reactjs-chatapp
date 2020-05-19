package com.tanerdiler.edd.event;

import jeventbus.shared.EventType;
import jeventbus.streaming.EventContext;
import jeventbus.streaming.EventDefinition;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EDDEventDefinition implements EventDefinition {

    private Integer id;
    private EventContext context;
    private Boolean reportable;
    private EventType eventType;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public EventContext getContext() {
        return context;
    }

    @Override
    public Boolean getReportable() {
        return reportable;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }
}
