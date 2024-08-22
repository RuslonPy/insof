package uz.insof.generatorservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CodeEvent {
    private Long userId;
    private String code;
    private LocalDateTime generatedAt;
}