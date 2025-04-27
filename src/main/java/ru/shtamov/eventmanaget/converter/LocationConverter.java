package ru.shtamov.eventmanaget.converter;

import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.model.domain.Location;
import ru.shtamov.eventmanaget.model.entity.LocationEntity;

@Component
public class LocationConverter {

    public LocationEntity toEntity(Location location){
        return new LocationEntity(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getCapacity(),
                location.getDescription()
        );
    }


    public Location toDomain(LocationEntity locationEntity){
          return new Location(
                  locationEntity.getId(),
                  locationEntity.getName(),
                  locationEntity.getAddress(),
                  locationEntity.getCapacity(),
                  locationEntity.getDescription()
          );
    }
}
