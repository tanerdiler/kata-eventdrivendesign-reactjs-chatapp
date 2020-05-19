package com.tanerdiler.edd.service;

import com.tanerdiler.edd.event.listener.MessageSentListener;
import jeventbus.core.Events;
import jeventbus.shared.EventSource;
import jeventbus.shared.ListenerTriggeringBreakerException;
import org.springframework.stereotype.Component;

import static com.tanerdiler.edd.event.EDDEventType.MESSAGE_DENIED;
import static java.lang.String.format;

@Component
public class StopWordChecker implements MessageSentListener {
    @Override
    public void onMessageSent(EventSource source) {
        String text = (String) source.get("message");
        if (text.contains("xxx")) {
            Events.event(MESSAGE_DENIED).fire(source.clone(MESSAGE_DENIED));
            throw new ListenerTriggeringBreakerException(format("Message {%s} contains stop word {xxx}", text));
        }
    }
}
