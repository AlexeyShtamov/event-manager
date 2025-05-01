package ru.shtamov.eventmanaget.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.model.domain.Event;
import ru.shtamov.eventmanaget.model.domain.EventStatus;
import ru.shtamov.eventmanaget.model.entity.EventEntity;

@Component
public class EventConverter {

    private final UserConverter userConverter;

    public EventConverter(@Lazy UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    public EventEntity toEntity(Event event) {
        return new EventEntity(
                event.getId(),
                event.getName(),
                event.getOwnerId(),
                event.getMaxPlaces(),
                event.getOccupiedPlaces(),
                event.getDate(),
                event.getCost(),
                event.getDuration(),
                event.getLocationId(),
                event.getStatus().name(),
                event.getEndTime()
        );
    }

    public Event toDomain(EventEntity entity) {
        return new Event(
                entity.getId(),
                entity.getName(),
                entity.getOwnerId(),
                entity.getMaxPlaces(),
                entity.getOccupiedPlaces(),
                entity.getDate(),
                entity.getCost(),
                entity.getDuration(),
                entity.getLocationId(),
                EventStatus.valueOf(entity.getStatus()),
                entity.getEndTime()
        );
    }

    public void updateEvent(Event foundedEvent, Event updatedEvent){
        foundedEvent.setDate(updatedEvent.getDate());
        foundedEvent.setDuration(updatedEvent.getDuration());
        foundedEvent.setCost(updatedEvent.getCost());
        foundedEvent.setMaxPlaces(updatedEvent.getMaxPlaces());
        foundedEvent.setLocationId(updatedEvent.getLocationId());
        foundedEvent.setName(updatedEvent.getName());
    }
}
