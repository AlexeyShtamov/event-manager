package ru.shtamov.eventmanaget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtamov.eventmanaget.model.dto.CreateLocationDto;
import ru.shtamov.eventmanaget.service.LocationService;
import ru.shtamov.eventmanaget.converter.LocationDtoConverter;
import ru.shtamov.eventmanaget.model.dto.LocationDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;
    private final LocationDtoConverter locationDtoConverter;

    @PostMapping
    public ResponseEntity<LocationDto> create(@RequestBody @Valid CreateLocationDto locationDto){
        LocationDto createdLocation =
                locationDtoConverter
                        .toDto(locationService.createLocation(locationDtoConverter.toDomain(locationDto)));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdLocation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> findOne(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationDtoConverter.toDto(locationService.findLocation(id)));
    }


    @GetMapping
    public ResponseEntity<List<LocationDto>> searchAll(){
        List<LocationDto> locationDtoList =
                locationService.findAllLocations().stream().map(locationDtoConverter::toDto).toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDto> update(@PathVariable Long id, @RequestBody @Valid CreateLocationDto locationDto){
        LocationDto updatedLocation =
                locationDtoConverter
                        .toDto(locationService.updateLocation(id, locationDtoConverter.toDomain(locationDto)));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedLocation);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        locationService.deleteLocation(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

}
