package com.tanerdiler.edd.controller;

import jeventbus.core.Events;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

import static com.tanerdiler.edd.event.EDDEventType.MESSAGE_READ;
import static com.tanerdiler.edd.event.EDDEventType.MESSAGE_SENT;
import static jeventbus.shared.Parameter.by;

@RestController
@RequestMapping("/message")
public class MessageReadCommand {

    @PutMapping("/read")
    public ResponseEntity<MessageReadResponse> sendMessage(@RequestBody MessageReadRequest request) {
        // DO SOMETHING
        //    Write into db
        Events.event(MESSAGE_READ)
                .fire(
                        by("event", MESSAGE_READ),
                        by("actor", request.getUsername()),
                        by("roomId", request.getRoomId()));

        return ResponseEntity.ok(new MessageReadResponse("SUCCESS"));
    }

    @Data
    public static class MessageReadRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String roomId;
    }

    @Getter
    @AllArgsConstructor
    public static class MessageReadResponse {
        @NotBlank
        private String state;
    }
}
