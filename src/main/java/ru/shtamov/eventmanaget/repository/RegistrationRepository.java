package ru.shtamov.eventmanaget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.shtamov.eventmanaget.model.entity.RegistrationEntity;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Long> {

    Optional<RegistrationEntity> findByEventId(Long eventId);

    boolean existsByEventIdAndUserId(Long eventId, Long userId);

    void removeByEventIdAndUserId(Long eventId, Long userId);

    List<RegistrationEntity> findAllByUserId(Long userId);

    @Query(value = """
           SELECT u.login FROM registration_entity r JOIN user_entity u
           ON r.user_id = u.id WHERE r.event_id = :eventId
           """
            , nativeQuery = true)
    List<String> findAllUserLoginByEventRegisterIdQuery(Long eventId);
}
