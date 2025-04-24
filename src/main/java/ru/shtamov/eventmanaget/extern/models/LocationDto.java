package ru.shtamov.eventmanaget.extern.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LocationDto(

        Integer id,

        @NotBlank
        String name,

        @NotBlank
        String address,

        Integer capacity,

        @Size(min = 3, max = 750)
        String description
){

}
