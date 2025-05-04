package ru.shtamov.eventmanaget.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.shtamov.eventmanaget.model.domain.EventStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class EventNotification {
    private Long eventId;
    private Long changerId;
    private Long ownerId;
    private List<String> subscribersLogins;

    FieldChange<String> name;
    FieldChange<Integer> maxPlaces;
    FieldChange<OffsetDateTime> date;
    FieldChange<Integer> cost;
    FieldChange<Integer> duration;
    FieldChange<Long> locationId;
    FieldChange<EventStatus> status;

    @Getter
    @AllArgsConstructor
    public static class FieldChange<T> {
        private T oldField;
        private T newField;

    }
}


