package com.tanerdiler.edd.controller;

import jeventbus.core.Events;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;

import static com.tanerdiler.edd.event.EDDEventType.USER_SIGNEDIN;
import static jeventbus.shared.Parameter.by;

@RestController
@RequestMapping("/user")
public class UserSignInCommand {

    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signin(@RequestBody SinginRequest request) {
        // DO SOMETHING
        //    Check credentials

        Events.event(USER_SIGNEDIN)
                .fire(
                        by("event", USER_SIGNEDIN),
                        by("actor", request.username),
                        by("username", request.username),
                        by("occurredAt", LocalDateTime.now()));

        return ResponseEntity.ok(new SigninResponse(request.getUsername()));
    }

    @Data
    public static class SinginRequest {
        @NotBlank
        private String username;
    }

    @Data
    @AllArgsConstructor
    public static class SigninResponse {
        private String username;
    }
}
