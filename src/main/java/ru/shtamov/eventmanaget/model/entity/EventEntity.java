package ru.shtamov.eventmanaget.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long ownerId;
    private Integer maxPlaces;
    private Integer occupiedPlaces;
    private OffsetDateTime date;
    private Integer cost;
    private Integer duration;
    private Long locationId;
    private String status;
    private OffsetDateTime endTime;

    @OneToMany(mappedBy = "event")
    private List<RegistrationEntity> registrations;

    public EventEntity(Long id, String name, Long ownerId, Integer maxPlaces, Integer occupiedPlaces, OffsetDateTime date, Integer cost, Integer duration, Long locationId, String status, OffsetDateTime endTime) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.maxPlaces = maxPlaces;
        this.occupiedPlaces = occupiedPlaces;
        this.date = date;
        this.cost = cost;
        this.duration = duration;
        this.locationId = locationId;
        this.status = status;
        this.endTime = endTime;
    }
}
