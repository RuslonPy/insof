package uz.insof.click_tracker_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.insof.click_tracker_service.entity.ClickEventRequest;
import uz.insof.click_tracker_service.entity.ClickEventResponse;
import uz.insof.click_tracker_service.service.ClickTrackerService;

import java.util.List;

@RestController
@RequestMapping("/click-tracker")
@RequiredArgsConstructor
public class ClickTrackerController {

    private final ClickTrackerService clickTrackerService;

    @PostMapping("/track")
    public ResponseEntity<?> trackClick(@RequestBody ClickEventRequest request) {
        clickTrackerService.saveClickEvent(request);
        List<ClickEventResponse> history = clickTrackerService.getClickHistory(request.getUsername());
        return ResponseEntity.ok(history);
    }

    @GetMapping("/history")
    public ResponseEntity<List<ClickEventResponse>> getClickHistory(@PathVariable String username) {
        List<ClickEventResponse> history = clickTrackerService.getClickHistory(username);
        return ResponseEntity.ok(history);
    }
}