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
            // Here, you should verify the code (e.g., a code generated earlier for the user)
            if (passwordEncoder.matches(code, user.getCodeHash())) {
                createSessionForUser(user);
                return true;
            }
        }

        return false;
    }

    private void createSessionForUser(UserEntity user) {
        // Generate a session token (you could use UUID or any other method)
        String sessionToken = UUID.randomUUID().toString();

        // Set session expiration (e.g., 4 minutes)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(4);

        SessionEntity session = new SessionEntity();
        session.setUserId(user.getId());
        session.setSessionToken(sessionToken);
        session.setCreatedAt(now);
        session.setExpiresAt(expiresAt);

        sessionRepository.save(session);

        // Store sessionToken in some form of storage (e.g., HttpSession or as a cookie)
        // Here, assuming HttpSession
        // httpSession.setAttribute("sessionToken", sessionToken);
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

