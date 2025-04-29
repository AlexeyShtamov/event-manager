package ru.shtamov.eventmanaget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shtamov.eventmanaget.model.entity.LocationEntity;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    Optional<LocationEntity> findByName(String name);


    boolean existsByName(String name);

    boolean existsById(Long id);
}
