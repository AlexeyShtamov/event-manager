package ru.shtamov.eventmanaget.converter;

import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.model.domain.Location;
import ru.shtamov.eventmanaget.model.dto.CreateLocationDto;
import ru.shtamov.eventmanaget.model.dto.LocationDto;

@Component
public class LocationDtoConverter {

    public LocationDto toDto(Location location){
        return new LocationDto(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getCapacity(),
                location.getDescription()
        );
    }

    public Location toDomain(CreateLocationDto locationDto){
        return new Location(
                null,
                locationDto.name(),
                locationDto.address(),
                locationDto.capacity(),
                locationDto.description()
        );
    }
}
