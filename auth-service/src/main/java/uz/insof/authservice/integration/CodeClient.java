package uz.insof.authservice.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "code-service", url = "http://localhost:8080")
public interface CodeClient {

    @GetMapping("/code-generator/latest/{username}")
    Integer getLastGeneratedCode(@PathVariable String username);
}