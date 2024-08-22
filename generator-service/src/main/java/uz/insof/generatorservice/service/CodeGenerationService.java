package uz.insof.generatorservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.insof.generatorservice.entity.CodeEntity;
import uz.insof.generatorservice.repositories.CodeRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class CodeGenerationService {
    @Autowired
    private CodeRepository codeRepository;

    public String generateCode(Long userId) {
        String code = String.format("%04d", new Random().nextInt(10000));

        CodeEntity codeEntity = new CodeEntity();
        codeEntity.setCode(code);
        codeEntity.setUserId(userId);
        codeEntity.setGeneratedAt(LocalDateTime.now());
        codeRepository.save(codeEntity);

        return code;
    }

    public Optional<CodeEntity> getLatestCode(Long userId) {
        return codeRepository.findByUserId(userId);
    }
}
