package ru.shtamov.eventmanaget.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserDto(
        @NotBlank
        String login,

        @NotBlank
        String password,

        @Min(1)
        @Max(250)
        Integer age
){
}
