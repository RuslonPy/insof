package uz.insof.authservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.insof.authservice.entity.SessionEntity;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    Optional<SessionEntity> findBySessionToken(String sessionToken);
    void deleteBySessionToken(String sessionToken);
}

