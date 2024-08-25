package uz.insof.click_tracker_service.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tracker-service", url = "http://localhost:8080/auth")
public interface ClickTrackerIntegration {

    @PostMapping("/logout")
    ResponseEntity<?> logout(@RequestParam String sessionToken);
}