package uz.insof.authservice.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.insof.authservice.config.AuthService;
import uz.insof.authservice.dto.LoginRequest;
import uz.insof.authservice.dto.UserResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String login, @RequestParam String code) {
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
