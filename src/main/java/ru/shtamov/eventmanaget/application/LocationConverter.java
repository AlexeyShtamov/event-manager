package ru.shtamov.eventmanaget.application;

import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.application.models.Location;
import ru.shtamov.eventmanaget.reps.models.LocationEntity;

@Component
public class LocationConverter {

    public LocationEntity toEntity(Location location){
        return new LocationEntity(
                location.getName(),
                location.getAddress(),
                location.getCapacity(),
                location.getDescription()
        );
    }


    public Location toDomain(LocationEntity locationEntity){
          return new Location(
                  Math.toIntExact(locationEntity.getId()),
                  locationEntity.getName(),
                  locationEntity.getAddress(),
                  locationEntity.getCapacity(),
                  locationEntity.getDescription()
          );
    }
}
