package ru.shtamov.eventmanaget.reps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shtamov.eventmanaget.reps.models.LocationEntity;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    Optional<LocationEntity> findByName(String name);
}
