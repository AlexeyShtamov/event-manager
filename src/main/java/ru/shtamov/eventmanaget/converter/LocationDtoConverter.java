package ru.shtamov.eventmanaget.converter;

import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.model.domain.Location;
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

    public Location toDomain(LocationDto locationDto){
        return new Location(
                locationDto.id(),
                locationDto.name(),
                locationDto.address(),
                locationDto.capacity(),
                locationDto.description()
        );
    }
}
