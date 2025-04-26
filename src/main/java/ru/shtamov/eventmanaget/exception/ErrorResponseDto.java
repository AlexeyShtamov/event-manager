package ru.shtamov.eventmanaget.exception;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        String message,
        String detailMessage,
        LocalDateTime dateTime
) {
}
