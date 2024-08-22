package uz.insof.generatorservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.insof.generatorservice.entity.CodeEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, Long> {
    List<CodeEntity> findAllByUsername(String username);
    Optional<CodeEntity> findFirstByUsernameOrderByGeneratedAtDesc(String username);
}

