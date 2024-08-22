package uz.insof.authservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.insof.authservice.entity.SessionEntity;
import uz.insof.authservice.entity.UserEntity;
import uz.insof.authservice.repositories.SessionRepository;
import uz.insof.authservice.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private UserRepository userRepository;

    private SessionRepository sessionRepository;

    private PasswordEncoder passwordEncoder;

    public boolean authenticate(String login, String code) {
        Optional<UserEntity> optionalUser = userRepository.findByLogin(login);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (passwordEncoder.matches(code, user.getCode())) {
                createSessionForUser(user);
                return true;
            }
        }

        return false;
    }

    private void createSessionForUser(UserEntity user) {
        String sessionToken = UUID.randomUUID().toString();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(4);

        SessionEntity session = new SessionEntity();
        session.setUserId(user.getId());
        session.setSessionToken(sessionToken);
        session.setCreatedAt(now);
        session.setExpiresAt(expiresAt);
        sessionRepository.save(session);
    }

    public UserEntity validateSession(String sessionToken) {
        Optional<SessionEntity> session = sessionRepository.findBySessionToken(sessionToken);
        if (session.isPresent() && session.get().getExpiresAt().isAfter(LocalDateTime.now())) {
            return userRepository.findById(session.get().getUserId()).orElse(null);
        }
        return null;
    }

    public void invalidateSession(String sessionToken) {
        sessionRepository.deleteBySessionToken(sessionToken);
    }
}

