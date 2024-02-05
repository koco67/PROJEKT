package de.htw.auth.controller;

import de.htw.auth.dto.UserRequest;
import de.htw.auth.dto.UserResponse;
import de.htw.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public String getEmailByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return authService.getEmailByToken(token);
    }
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return authService.createUser(userRequest);
    }
    @PostMapping(path = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean verifyAdmin(@RequestBody UserRequest userRequest) {
        return authService.verifyAdmin(userRequest);
    }

}
