package ru.shtamov.eventmanaget.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shtamov.eventmanaget.converter.LocationConverter;
import ru.shtamov.eventmanaget.model.domain.Location;
import ru.shtamov.eventmanaget.exception.IsAlreadyExistException;
import ru.shtamov.eventmanaget.model.entity.LocationEntity;
import ru.shtamov.eventmanaget.repositorie.LocationRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationConverter locationConverter;

    public Location createLocation(Location location){

        if (locationRepository.findByName(location.getName()).isPresent())
            throw new IsAlreadyExistException("Локация с названием %s уже существует".formatted(location.getName()));

        LocationEntity createdLocEntity = locationRepository.save(locationConverter.toEntity(location));

        log.info("Локация с именем {} и id {} сохранена", createdLocEntity.getName(), createdLocEntity.getId());

        return locationConverter.toDomain(createdLocEntity);

    }

    public Location findLocation(Integer id){
        Location foundedLocation = locationConverter.toDomain(locationRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NoSuchElementException("Нет локации с id " + id)));

        log.info("Локация с id {} найдена, имя:  {}", id, foundedLocation.getName());

        return foundedLocation;
    }

    public List<Location> findAllLocations(){
        List<Location> locations =
                locationRepository.findAll().stream().map(locationConverter::toDomain).toList();

        log.info("Найдены все локации");

        return locations;
    }

    public Location updateLocation(Integer id, Location location){
        LocationEntity foundedLocation = locationRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NoSuchElementException("Нет локации с id " + id));

        foundedLocation.setAddress(location.getAddress());
        foundedLocation.setDescription(location.getDescription());
        foundedLocation.setCapacity(location.getCapacity());
        foundedLocation.setName(location.getName());

        Location updatedLocation = locationConverter.toDomain(locationRepository.save(foundedLocation));

        log.info("Локация с id {} обновлена", id);

        return updatedLocation;

    }

    public void deleteLocation(Integer id){
        locationRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NoSuchElementException("Нет локации с id " + id));

        locationRepository.deleteById(Long.valueOf(id));

        log.info("Локация с id {} удалена", id);
    }


}
