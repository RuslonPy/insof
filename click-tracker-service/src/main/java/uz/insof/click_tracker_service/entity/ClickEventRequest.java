package uz.insof.click_tracker_service.entity;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClickEventRequest {

    private LocalDateTime clickedAt;
    private Integer xCoordinate;
    private Integer yCoordinate;
    private String username;

}
