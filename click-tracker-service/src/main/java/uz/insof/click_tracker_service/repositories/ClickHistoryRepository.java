package uz.insof.click_tracker_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.insof.click_tracker_service.entity.ClickHistoryEntity;

import java.util.List;

@Repository
public interface ClickHistoryRepository extends JpaRepository<ClickHistoryEntity, Long> {
    List<ClickHistoryEntity> findByUserId(Long userId);
}
