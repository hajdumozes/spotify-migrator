package com.mozeshajdu.spotifymigrator.auth.web;

import com.mozeshajdu.spotifymigrator.auth.entity.UserCredential;
import com.mozeshajdu.spotifymigrator.auth.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;

    @GetMapping(value = "/uri")
    public ResponseEntity<String> getAuthUri() {
        String uri = authService.generateAuthorizationCodeUri();
        return ResponseEntity.ok(uri);
    }

    @GetMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserCredential> initializeTokens(@RequestParam String code) {
        UserCredential userCredentials = authService.initializeTokens(code);
        return ResponseEntity.ok(userCredentials);
    }
}
