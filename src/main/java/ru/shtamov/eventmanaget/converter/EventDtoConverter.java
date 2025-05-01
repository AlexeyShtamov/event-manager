package ru.shtamov.eventmanaget.converter;

import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.model.domain.Event;
import ru.shtamov.eventmanaget.model.domain.SearchEvent;
import ru.shtamov.eventmanaget.model.dto.CreateEventDto;
import ru.shtamov.eventmanaget.model.dto.EventDto;
import ru.shtamov.eventmanaget.model.dto.SearchEventDto;

@Component
public class EventDtoConverter {

    public EventDto toDto(Event event){
        return  new EventDto(
                event.getId(),
                event.getName(),
                event.getOwnerId(),
                event.getMaxPlaces(),
                event.getOccupiedPlaces(),
                event.getDate(),
                event.getCost(),
                event.getDuration(),
                event.getLocationId(),
                event.getStatus().name()
        );
    }

    public Event toDomain(CreateEventDto eventDto) {
        return new Event(
                eventDto.name(),
                eventDto.maxPlaces(),
                eventDto.date(),
                eventDto.cost(),
                eventDto.duration(),
                eventDto.locationId()
        );
    }

    public SearchEvent toSearchDomain(SearchEventDto eventDto) {
        return new SearchEvent(
                eventDto.name(),
                eventDto.placesMin(),
                eventDto.placesMax(),
                eventDto.dateStartAfter(),
                eventDto.dateStartBefore(),
                eventDto.costMin(),
                eventDto.costMax(),
                eventDto.durationMin(),
                eventDto.durationMax(),
                eventDto.locationId(),
                eventDto.eventStatus()
        );
    }

}
