package uz.insof.click_tracker_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "click_history")
@Setter
@Getter
public class ClickHistoryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDateTime clickedAt;

    @Column(nullable = false)
    private Integer xCoordinate;

    @Column(nullable = false)
    private Integer yCoordinate;
}
