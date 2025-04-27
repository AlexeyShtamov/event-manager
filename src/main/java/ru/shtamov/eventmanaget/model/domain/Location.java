package ru.shtamov.eventmanaget.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Location {

    private Long id;
    private String name;
    private String address;
    private Integer capacity;
    private String description;

}
