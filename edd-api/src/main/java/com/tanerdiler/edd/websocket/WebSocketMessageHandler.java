package com.tanerdiler.edd.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import jeventbus.core.Events;
import jeventbus.shared.EventSource;
import jeventbus.shared.Parameter;
import jeventbus.streaming.EventMessage;
import jeventbus.streaming.EventToMessageConverter;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

import static com.tanerdiler.edd.event.EDDEventType.WEBSOCKET_ESTABLISHED;
import static jeventbus.shared.Parameter.by;

@Component
@RequiredArgsConstructor
public class WebSocketMessageHandler extends TextWebSocketHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		System.out.println(message);
		try {
			if (message.getPayload().contains("connect")) {
					WebSocketConnectionRequest connReq = objectMapper.readValue(message.asBytes(), WebSocketConnectionRequest.class);
					Events.event(WEBSOCKET_ESTABLISHED)
							.fire(
								by("username", connReq.connect),
								by("websocket", session));
			} else {
				EventMessage eventMessage = objectMapper.readValue(message.getPayload().getBytes(), EventMessage.class);
				EventSource source = EventToMessageConverter.convert(eventMessage);
				Events.event(source.eventType()).fire(source);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Data
	public static class WebSocketConnectionRequest {
		private String connect;
	}

}