package ru.shtamov.eventmanaget.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class SearchEvent {
    private String name;
    private Integer placesMin;
    private Integer placesMax;
    private OffsetDateTime dateStartAfter;
    private OffsetDateTime dateStartBefore;
    private Integer costMin;
    private Integer contMax;
    private Integer durationMin;
    private Integer durationMax;
    private Long locationId;
    private String eventStatus;
}
