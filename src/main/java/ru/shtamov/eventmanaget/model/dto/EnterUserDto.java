package ru.shtamov.eventmanaget.model.dto;

import jakarta.validation.constraints.NotBlank;

public record EnterUserDto(
        @NotBlank
        String login,

        @NotBlank
        String password
) {
}
