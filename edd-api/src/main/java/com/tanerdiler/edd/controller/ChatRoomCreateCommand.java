package com.tanerdiler.edd.controller;

import jeventbus.core.Events;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

import static com.tanerdiler.edd.event.EDDEventType.CHAT_ROOM_CREATED;
import static com.tanerdiler.edd.event.EDDEventType.MESSAGE_SENT;
import static jeventbus.shared.Parameter.by;

@RestController
@RequestMapping("/room")
public class ChatRoomCreateCommand {

    @PostMapping("/add")
    public ResponseEntity<ChatRoomCreateResponse> sendMessage(@RequestBody ChatRoomCreateRequest request) {
        // DO SOMETHING
        //    Persist chat room

        Events.event(CHAT_ROOM_CREATED)
                .fire(
                        by("event", CHAT_ROOM_CREATED),
                        by("actor", request.getUsername()),
                        by("roomId", request.getRoomId()));

        return ResponseEntity.ok(new ChatRoomCreateResponse("SUCCESS"));
    }

    @Data
    public static class ChatRoomCreateRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String roomId;
    }

    @Getter
    @AllArgsConstructor
    public static class ChatRoomCreateResponse {
        @NotBlank
        private String state;
    }
}
