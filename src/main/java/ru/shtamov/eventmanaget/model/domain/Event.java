package ru.shtamov.eventmanaget.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Event{
    private Long id;
    private String name;
    private Long ownerId;
    private Integer maxPlaces;
    private Integer occupiedPlaces;
    private OffsetDateTime date;
    private Integer cost;
    private Integer duration;
    private Long locationId;
    private EventStatus status;
    private OffsetDateTime endTime;

    public Event(String name, Integer maxPlaces, OffsetDateTime date, Integer cost, Integer duration, Long locationId) {
        this.name = name;
        this.maxPlaces = maxPlaces;
        this.date = date;
        this.cost = cost;
        this.duration = duration;
        this.locationId = locationId;
        this.occupiedPlaces = 0;
    }
}
