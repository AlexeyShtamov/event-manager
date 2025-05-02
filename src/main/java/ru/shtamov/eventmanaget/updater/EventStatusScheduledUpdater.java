package ru.shtamov.eventmanaget.updater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.converter.EventConverter;
import ru.shtamov.eventmanaget.helper.KafkaHelper;
import ru.shtamov.eventmanaget.model.domain.EventStatus;
import ru.shtamov.eventmanaget.model.entity.EventEntity;
import ru.shtamov.eventmanaget.repository.EventRepository;
import ru.shtamov.eventmanaget.repository.RegistrationRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventStatusScheduledUpdater {

    private final EventRepository eventRepository;
    private final EventConverter eventConverter;
    private final RegistrationRepository registrationRepository;
    private final KafkaHelper kafkaHelper;

    @Scheduled(cron = "${event.stats.cron}")
    public void updateEventStatuses() {
        log.info("EventStatusScheduledUpdater начал свою работу");

        var startedEvents = eventRepository.findStartedEventsWithStatus(EventStatus.WAIT_START);
        startedEvents.forEach(event -> notify(event, EventStatus.WAIT_START, EventStatus.STARTED));

        var endedEvents = eventRepository.findEndedEventsWithStatus(EventStatus.STARTED);
        endedEvents.forEach(event -> notify(event, EventStatus.STARTED, EventStatus.FINISHED));
    }


    private void notify(EventEntity event, EventStatus oldStatus, EventStatus newStatus) {

        eventRepository.changeEventStatus(event.getId(), newStatus);

        kafkaHelper.getEventNotificationWithChangedStatus(
                eventConverter.toDomain(event),
                oldStatus,
                newStatus,
                null,
                registrationRepository.findAllUserLoginByEventRegisterIdQuery(event.getId()));
    }
}
