package ru.shtamov.eventmanaget.model.domain;

import lombok.Getter;

@Getter
public class Registration {
    private Long id;
    private Long userId;
    private Event event;

    public Registration(Long userId, Event event) {
        this.userId = userId;
        this.event = event;
    }
}
