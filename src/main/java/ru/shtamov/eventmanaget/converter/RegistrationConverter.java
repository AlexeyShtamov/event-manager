package ru.shtamov.eventmanaget.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.model.domain.Registration;
import ru.shtamov.eventmanaget.model.entity.RegistrationEntity;

@Component
@RequiredArgsConstructor
public class RegistrationConverter {
    private final EventConverter eventConverter;

    public RegistrationEntity toEntity(Registration registration){
        return new RegistrationEntity(
                registration.getUserId(),
                eventConverter.toEntity(registration.getEvent())
        );
    }

    public Registration toDomain(RegistrationEntity entity) {
        return new Registration(
                entity.getUserId(),
                eventConverter.toDomain(entity.getEvent())
        );
    }
}
