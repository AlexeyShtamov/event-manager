package ru.shtamov.eventmanaget.model.dto;

public record UserDto(
        Long id,
        String login,
        Integer age,
        String role
) {
}
