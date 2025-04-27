package ru.shtamov.eventmanaget.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateLocationDto(
        @NotBlank
        String name,

        @NotBlank
        String address,

        @Min(5)
        Integer capacity,

        @NotBlank
        String description
) {
}
