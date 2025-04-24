package ru.shtamov.eventmanaget.extern.exceptions;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        String message,
        String detailMessage,
        LocalDateTime dateTime
) {
}
