package ru.shtamov.eventmanaget.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    public RegistrationEntity(Long userId, EventEntity event) {
        this.userId = userId;
        this.event = event;
    }
}
