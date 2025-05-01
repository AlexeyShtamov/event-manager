package ru.shtamov.eventmanaget.model.dto;


import java.time.OffsetDateTime;

public record EventDto(

        Long id,
        String name,
        Long ownerId,
        Integer maxPlaces,
        Integer occupiedPlaces,
        OffsetDateTime date,
        Integer cost,
        Integer duration,
        Long locationId,
        String status

) {
}
