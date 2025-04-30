package ru.shtamov.eventmanaget.updater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.model.domain.EventStatus;
import ru.shtamov.eventmanaget.repository.EventRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventStatusScheduledUpdater {

    private final EventRepository eventRepository;

    @Scheduled(cron = "${event.stats.cron}")
    public void updateEventStatuses() {
        log.info("EventStatusScheduledUpdater начал свою работу");

        var startedEvents = eventRepository.findStartedEventsWithStatus(EventStatus.WAIT_START);
        startedEvents.forEach(event ->
                eventRepository.changeEventStatus(event.getId(), EventStatus.STARTED)
        );

        var endedEvents = eventRepository.findEndedEventsWithStatus(EventStatus.STARTED);
        endedEvents.forEach(event ->
                eventRepository.changeEventStatus(event.getId(), EventStatus.FINISHED)
        );
    }


}
