package ru.shtamov.eventmanaget.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LocationDto(

        Long id,

        @NotBlank
        String name,

        @NotBlank
        String address,

        @Min(5)
        Integer capacity,

        String description
){

}
