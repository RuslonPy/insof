package uz.insof.generatorservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.insof.generatorservice.entity.CodeHistory;
import uz.insof.generatorservice.service.CodeGenerationService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/code-generator")
public class CodeGeneratorController {
    private final CodeGenerationService codeGenerationService;

    @GetMapping("/generate/{login}")
    public Integer getCode(@PathVariable String login) {
        return codeGenerationService.generateCode(login);
    }

    @GetMapping("/history/{login}")
    public List<CodeHistory> getCodeHistory(@PathVariable String login){
        return codeGenerationService.getCodeHistories(login);
    }
}

