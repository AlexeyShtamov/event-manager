package ru.shtamov.eventmanaget.model.dto;

public record UserDto(
        Integer id,
        String login,
        Integer age,
        String role
) {
}
