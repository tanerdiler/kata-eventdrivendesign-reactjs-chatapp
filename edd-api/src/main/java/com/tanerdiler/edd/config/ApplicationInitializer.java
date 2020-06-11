package com.tanerdiler.edd.config;

import com.tanerdiler.edd.user.OnlineUsers;
import com.tanerdiler.edd.service.StopWordChecker;
import com.tanerdiler.edd.service.UINotifier;
import jeventbus.core.Events;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.tanerdiler.edd.config.ApplicationContextProvider.getBean;
import static com.tanerdiler.edd.event.EDDEventType.*;

@Component
public class ApplicationInitializer {

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Events.event(WEBSOCKET_ESTABLISHED).add(getBean(OnlineUsers.class));
        Events.event(USER_SIGNEDIN).add(getBean(UINotifier.class)).add(getBean(OnlineUsers.class)).add(getBean(UINotifier.class));
        Events.event(MESSAGE_SENT).add(getBean(StopWordChecker.class)).add(getBean(UINotifier.class));
        Events.event(MESSAGE_DENIED).add(getBean(UINotifier.class));
        Events.event(MESSAGE_READ).add(getBean(UINotifier.class));
        Events.event(CHAT_ROOM_CREATED).add(getBean(UINotifier.class));
    }

}