package ru.shtamov.eventmanaget.model.kafka;

import java.util.List;

public record EventNotification(
        Long eventId,
        Long changerId,
        Long ownerId,
        List<String> changedFields,
        List<String> subscribersLogins
) {
}
