package uz.insof.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.insof.authservice.config.AuthService;
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
        boolean isAuthenticated = authService.authenticate(login, code);

        if (isAuthenticated) {
            return ResponseEntity.ok("Authenticated");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login or code");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String sessionToken) {
        authService.invalidateSession(sessionToken);
        return ResponseEntity.ok("Logged out");
    }
}