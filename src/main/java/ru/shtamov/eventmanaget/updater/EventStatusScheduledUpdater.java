package ru.shtamov.eventmanaget.updater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.converter.EventConverter;
import ru.shtamov.eventmanaget.model.domain.EventStatus;
import ru.shtamov.eventmanaget.model.entity.EventEntity;
import ru.shtamov.eventmanaget.model.kafka.EventNotification;
import ru.shtamov.eventmanaget.producer.EventNotificationProducer;
import ru.shtamov.eventmanaget.repository.EventRepository;
import ru.shtamov.eventmanaget.repository.RegistrationRepository;
import ru.shtamov.eventmanaget.service.EventPermissionService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventStatusScheduledUpdater {

    private final EventRepository eventRepository;
    private final EventPermissionService eventPermissionService;
    private final RegistrationRepository registrationRepository;
    private final EventNotificationProducer eventNotificationProducer;

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

        eventNotificationProducer.eventSent(new EventNotification(
                event.getId(),
                eventPermissionService.getAuthenticatedUserId(),
                event.getOwnerId(),
                List.of(String.format("Старый статус: %s, Новый статус: %s"
                        , oldStatus, newStatus)),
                registrationRepository.findAllUserLoginByEventRegisterIdQuery(event.getId())
        ));
    }
}
