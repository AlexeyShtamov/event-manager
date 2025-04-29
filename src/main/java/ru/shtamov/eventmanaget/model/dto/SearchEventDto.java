package ru.shtamov.eventmanaget.model.dto;

import java.time.OffsetDateTime;

public record SearchEventDto(
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
) {
}
