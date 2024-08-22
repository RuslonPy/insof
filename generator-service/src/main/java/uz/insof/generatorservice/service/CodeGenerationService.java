package uz.insof.generatorservice.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.insof.generatorservice.entity.CodeEntity;
import uz.insof.generatorservice.entity.CodeHistory;
import uz.insof.generatorservice.repositories.CodeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CodeGenerationService {

    private CodeRepository codeRepository;

    public Integer generateCode(String username) {
        Integer code = new Random().nextInt(9000) + 1000;

        CodeEntity codeEntity = new CodeEntity();
        codeEntity.setCode(code);
        codeEntity.setUsername(username);
        codeEntity.setGeneratedAt(LocalDateTime.now());
        codeRepository.save(codeEntity);

        return code;
    }

    public List<CodeHistory> getCodeHistories(String username) {
        List<CodeEntity> codeEntities = codeRepository.findAllByUsername(username);

        List<CodeHistory> codeHistories = codeEntities.stream()
                .map(entity -> {
                    CodeHistory history = new CodeHistory();
                    history.setLogin(entity.getUsername());
                    history.setCode(entity.getCode());
                    history.setGeneratedAt(entity.getGeneratedAt());
                    return history;
                })
                .collect(Collectors.toList());

        return codeHistories;

    }
}
