package uz.insof.authservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.insof.authservice.entity.SessionEntity;
import uz.insof.authservice.entity.UserEntity;
import uz.insof.authservice.integration.CodeClient;
import uz.insof.authservice.repositories.SessionRepository;
import uz.insof.authservice.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final SessionRepository sessionRepository;

    private final PasswordEncoder passwordEncoder;
    private final CodeClient codeClient;

    public boolean authenticate(String login, Integer code) {
        Integer response = codeClient.getLastGeneratedCode(login);

        if (Objects.equals(code, response)) {
            createSessionForUser(login);
            return true;
        }
        return false;
    }

    private void createSessionForUser(String user) {
        String sessionToken = UUID.randomUUID().toString();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(4);

        SessionEntity session = new SessionEntity();
        session.setUsername(user);
        session.setSessionToken(sessionToken);
        session.setCreatedAt(now);
        session.setExpiresAt(expiresAt);
        sessionRepository.save(session);
    }

    public UserEntity validateSession(String sessionToken) {
        Optional<SessionEntity> session = sessionRepository.findBySessionToken(sessionToken);
        if (session.isPresent() && session.get().getExpiresAt().isAfter(LocalDateTime.now())) {
            return userRepository.findByLogin(session.get().getUsername()).orElse(null);
        }
        return null;
    }

    public void invalidateSession(String sessionToken) {
        sessionRepository.deleteBySessionToken(sessionToken);
    }
}