package ru.shtamov.eventmanaget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.shtamov.eventmanaget.model.domain.EventStatus;
import ru.shtamov.eventmanaget.model.entity.EventEntity;

import java.time.OffsetDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Query(value = """
    SELECT * from event_entity e
    WHERE (:name IS NULL OR e.name = :name)
    AND (:placesMin IS NULL OR e.max_places > :placesMin)
    AND (:placesMax IS NULL OR e.max_places < :placesMax)
    AND (:costMin IS NULL OR e.cost > :costMin)
    AND (:costMax IS NULL OR e.cost < :costMax)
    AND (:durationMin IS NULL OR e.duration > :durationMin)
    AND (:durationMax IS NULL OR e.duration < :durationMax)
    AND (:locationId IS NULL OR e.location_id = :locationId)
    AND (:eventStatus IS NULL OR e.status = :eventStatus)
    AND (CAST(:dateStartAfter as date) IS NULL OR e.date >= :dateStartAfter)
    AND (CAST(:dateStartBefore as date) IS NULL OR e.date <= :dateStartBefore)
    """, nativeQuery = true)
    List<EventEntity> searchEvents(
            String name,
            Integer placesMin,
            Integer placesMax,
            OffsetDateTime dateStartAfter,
            OffsetDateTime dateStartBefore,
            Integer costMin,
            Integer costMax,
            Integer durationMin,
            Integer durationMax,
            Long locationId,
            String eventStatus
    );

    @Query("SELECT e FROM EventEntity e WHERE e.date >= CURRENT_DATE AND e.status = :#{#eventStatus.name()}")
    List<EventEntity> findStartedEventsWithStatus(EventStatus eventStatus);

    @Query("SELECT e FROM EventEntity e WHERE e.endTime >= CURRENT_DATE AND e.status = :#{#eventStatus.name()}")
    List<EventEntity> findEndedEventsWithStatus(EventStatus eventStatus);

    List<EventEntity> findAllByOwnerId(Long ownerId);

    @Modifying
    @Transactional
    @Query("UPDATE EventEntity e SET e.status = :#{#eventStatus.name()} WHERE e.id = :eventId")
    void changeEventStatus(Long eventId, EventStatus eventStatus);
}
