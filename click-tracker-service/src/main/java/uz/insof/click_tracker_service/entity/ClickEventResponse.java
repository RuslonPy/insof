package uz.insof.click_tracker_service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClickEventResponse {
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime clickedAt;
    private Integer xCoordinate;
    private Integer yCoordinate;
}
