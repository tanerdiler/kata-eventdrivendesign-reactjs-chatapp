package com.tanerdiler.edd.controller;

import jeventbus.core.Events;
import jeventbus.shared.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

import static com.tanerdiler.edd.event.EDDEventType.MESSAGE_SENT;
import static jeventbus.shared.Parameter.by;

@RestController
@RequestMapping("/message")
public class MessageSendCommand {

    @PostMapping("/send")
    public ResponseEntity<MessageSendResponse> sendMessage(@RequestBody MessageSendRequest request) {
        // DO SOMETHING
        //    Write into db
        Events.event(MESSAGE_SENT)
                .fire(
                        by("event", MESSAGE_SENT),
                        by("actor", request.getUsername()),
                        by("message", request.getMessage()),
                        by("roomId", request.getRoomId()));

        return ResponseEntity.ok(new MessageSendResponse("SUCCESS"));
    }

    @Data
    public static class MessageSendRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String message;
        @NotBlank
        private String roomId;
    }

    @Getter
    @AllArgsConstructor
    public static class MessageSendResponse {
        @NotBlank
        private String state;
    }
}
