package ru.shtamov.eventmanaget.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.model.domain.Event;
import ru.shtamov.eventmanaget.model.domain.EventStatus;
import ru.shtamov.eventmanaget.model.kafka.EventNotification;
import ru.shtamov.eventmanaget.producer.EventNotificationProducer;

import java.util.List;


@RequiredArgsConstructor
@Component
public class KafkaHelper {

    private final EventNotificationProducer eventNotificationProducer;

    public void getEventNotificationWithChangedFields(
            Event oldEvent,
            Event newEvent,
            Long changerId,
            List<String> subscribersLogins) {

        EventNotification eventNotification = new EventNotification();

        if (newEvent.getName() != null && !oldEvent.getName().equals(newEvent.getName()))
            eventNotification.setName(new EventNotification.FieldChange<>(oldEvent.getName(), newEvent.getName()));

        if (newEvent.getMaxPlaces() != null && !oldEvent.getMaxPlaces().equals(newEvent.getMaxPlaces()))
            eventNotification.setMaxPlaces(new EventNotification.FieldChange<>(oldEvent.getMaxPlaces(), newEvent.getMaxPlaces()));

        if (newEvent.getDate() != null && !oldEvent.getDate().equals(newEvent.getDate()))
            eventNotification.setDate(new EventNotification.FieldChange<>(oldEvent.getDate(), newEvent.getDate()));

        if (newEvent.getCost() != null && !oldEvent.getCost().equals(newEvent.getCost()))
            eventNotification.setCost(new EventNotification.FieldChange<>(oldEvent.getCost(), newEvent.getCost()));

        if (newEvent.getDuration() != null && !oldEvent.getDuration().equals(newEvent.getDuration()))
            eventNotification.setDuration(new EventNotification.FieldChange<>(oldEvent.getDuration(), newEvent.getDuration()));

        if (newEvent.getLocationId() != null && !oldEvent.getLocationId().equals(newEvent.getLocationId()))
            eventNotification.setLocationId(new EventNotification.FieldChange<>(oldEvent.getLocationId(), newEvent.getLocationId()));

        if (newEvent.getStatus() != null && !oldEvent.getStatus().equals(newEvent.getStatus()))
            eventNotification.setStatus(new EventNotification.FieldChange<>(oldEvent.getStatus(), newEvent.getStatus()));

        eventNotification.setEventId(oldEvent.getId());
        eventNotification.setChangerId(changerId);
        eventNotification.setOwnerId(oldEvent.getOwnerId());
        eventNotification.setSubscribersLogins(subscribersLogins);

        eventNotificationProducer.eventSent(eventNotification);
    }

    public void getEventNotificationWithChangedStatus(
            Event event,
            EventStatus oldStatus,
            EventStatus newStatus,
            Long changerId,
            List<String> subscribersLogins) {

        EventNotification eventNotification = new EventNotification();

        eventNotification.setStatus(new EventNotification.FieldChange<>(oldStatus, newStatus));

        eventNotification.setEventId(event.getId());
        eventNotification.setChangerId(changerId);
        eventNotification.setOwnerId(event.getOwnerId());
        eventNotification.setSubscribersLogins(subscribersLogins);

        eventNotificationProducer.eventSent(eventNotification);
    }
}
