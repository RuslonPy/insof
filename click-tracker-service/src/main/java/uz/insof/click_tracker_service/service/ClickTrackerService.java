package uz.insof.click_tracker_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.insof.click_tracker_service.entity.ClickEventRequest;
import uz.insof.click_tracker_service.entity.ClickEventResponse;
import uz.insof.click_tracker_service.entity.ClickHistoryEntity;
import uz.insof.click_tracker_service.repositories.ClickHistoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClickTrackerService {
    private final ClickHistoryRepository clickHistoryRepository;

    public void saveClickEvent(ClickEventRequest request) {
        Optional.ofNullable(request)
                .map(req -> {
                    ClickHistoryEntity entity = new ClickHistoryEntity();
                    entity.setUsername(req.getUsername());
                    entity.setClickedAt(req.getClickedAt());
                    entity.setXCoordinate(req.getXCoordinate());
                    entity.setYCoordinate(req.getYCoordinate());
                    return entity;
                })
                .ifPresent(clickHistoryRepository::save);
    }

    public List<ClickEventResponse> getClickHistory(String username) {
        List<ClickHistoryEntity> clickHistoryEntities = clickHistoryRepository.findAllByUsername(username);

        return clickHistoryEntities.stream()
                .map(entity -> {
                    ClickEventResponse response = new ClickEventResponse();
                    response.setClickedAt(entity.getClickedAt());
                    response.setXCoordinate(entity.getXCoordinate());
                    response.setYCoordinate(entity.getYCoordinate());
                    return response;
                })
                .collect(Collectors.toList());
    }
}
