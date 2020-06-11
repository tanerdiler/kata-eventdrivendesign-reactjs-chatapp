package com.tanerdiler.edd.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanerdiler.edd.websocket.WebSocketMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

	private final ObjectMapper jsonMapper;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new WebSocketMessageHandler(jsonMapper), "/event").setAllowedOrigins("*");
	}

}