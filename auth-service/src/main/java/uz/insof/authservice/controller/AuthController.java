package uz.insof.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.insof.authservice.service.AuthService;
import uz.insof.authservice.dto.LoginRequest;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:63342")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String login = loginRequest.getLogin();
        Integer code = loginRequest.getCode();
        ResponseEntity<String> authenticated = authService.authenticate(login, code);

        if (authenticated.getStatusCode().equals(HttpStatus.OK)) {
            return ResponseEntity.ok(authenticated);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login or code");
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String sessionToken) {
        return authService.getUser(sessionToken);

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String sessionToken) {
        authService.invalidateSession(sessionToken);
        return ResponseEntity.ok("Logged out");
    }
}