package uz.insof.generatorservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import uz.insof.generatorservice.entity.CodeEvent;

@Service
@RequiredArgsConstructor
public class CodeEventService {

    private final String EVENT_TOPIC = "code_event";
    private KafkaTemplate<String, CodeEvent> kafkaTemplate;

    public void sendCodeEvent(CodeEvent event) {
        kafkaTemplate.send(EVENT_TOPIC, event);
    }
}