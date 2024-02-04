package de.htw.auth.controller;

import de.htw.auth.dto.UserRequest;
import de.htw.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verifyUser(@RequestBody UserRequest userRequest) {
        return authService.verifyUser(userRequest);
    }

    @GetMapping
    public Boolean checkToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return authService.checkToken(token);
    }

    @GetMapping(path = "/token", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getUserIdByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return authService.getUserIdByToken(token);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean createUser(@RequestBody UserRequest userRequest) {
        return authService.createUser(userRequest);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean verifyAdmin(@RequestBody UserRequest userRequest) {
        return authService.verifyAdmin(userRequest);
    }

}
