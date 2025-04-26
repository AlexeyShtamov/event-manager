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

        if (locationRepository.existsByName(location.getName()))
            throw new IsAlreadyExistException("Локация с названием %s уже существует".formatted(location.getName()));

        Location cretedLocation = locationConverter
                .toDomain(locationRepository.save(locationConverter.toEntity(location)));

        log.info("Локация с именем {} и id {} сохранена", cretedLocation.getName(), cretedLocation.getId());

        return cretedLocation;

    }

    public Location findLocation(Long id){
        Location foundedLocation = locationConverter.toDomain(locationRepository.findById(id)
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

    public Location updateLocation(Long id, Location location){
        if(!locationRepository.existsById(id))
                throw new NoSuchElementException("Нет локации с id " + id);

        location.setId(id);
        Location updatedLocation = locationConverter
                .toDomain(locationRepository.save(locationConverter.toEntity(location)));

        log.info("Локация с id {} обновлена", id);

        return updatedLocation;

    }

    public void deleteLocation(Long id){
        if (!locationRepository.existsById(id))
                throw new NoSuchElementException("Нет локации с id " + id);

        locationRepository.deleteById(id);

        log.info("Локация с id {} удалена", id);
    }


}
