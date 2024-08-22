package uz.insof.authservice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.insof.authservice.dto.CodeEvent;
import uz.insof.authservice.entity.UserEntity;
import uz.insof.authservice.repositories.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CodeEventListener {

    private UserRepository userRepository;
    
    private PasswordEncoder passwordEncoder;

    @KafkaListener(topics = "code_event", groupId = "auth-service")
    public void listen(CodeEvent codeEvent) {
        Optional<UserEntity> optionalUser = userRepository.findById(codeEvent.getUserId());

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.setCode(passwordEncoder.encode(codeEvent.getCode()));
            user.setRegisteredAt(codeEvent.getGeneratedAt());
            userRepository.save(user);
        }
    }
}

