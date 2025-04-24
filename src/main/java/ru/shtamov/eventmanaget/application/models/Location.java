package ru.shtamov.eventmanaget.application.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Location {

    private Integer id;
    private String name;
    private String address;
    private Integer capacity;
    private String description;

}
