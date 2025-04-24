package ru.shtamov.eventmanaget.extern;

import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.application.models.Location;
import ru.shtamov.eventmanaget.extern.models.LocationDto;

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
