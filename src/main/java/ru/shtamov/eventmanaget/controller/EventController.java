package ru.shtamov.eventmanaget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtamov.eventmanaget.converter.EventDtoConverter;
import ru.shtamov.eventmanaget.model.domain.Event;
import ru.shtamov.eventmanaget.model.dto.CreateEventDto;
import ru.shtamov.eventmanaget.model.dto.EventDto;
import ru.shtamov.eventmanaget.model.dto.SearchEventDto;
import ru.shtamov.eventmanaget.service.EventService;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final EventDtoConverter eventDtoConverter;

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody @Valid CreateEventDto eventDto) {

        Event event = eventDtoConverter.toDomain(eventDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventDtoConverter.toDto(eventService.createEvent(event)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelEvent(@PathVariable Long id) throws AccessDeniedException {
        eventService.deleteEvent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(@PathVariable Long id) {

        Event event = eventService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventDtoConverter.toDto(event));
    }

    @PostMapping("/search")
    public ResponseEntity<List<EventDto>> searchEvents(@RequestBody SearchEventDto eventDto) {
        List<EventDto> eventDtos =
                eventService.searchEvents(eventDtoConverter.toSearchDomain(eventDto))
                        .stream().map(eventDtoConverter::toDto).toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventDtos);
    }

    @GetMapping("/my")
    public ResponseEntity<List<EventDto>> getCreatedEvents() {

        List<EventDto> eventDtos = eventService.findCreatedEvents().stream().map(eventDtoConverter::toDto).toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@RequestBody @Valid CreateEventDto eventDto, @PathVariable Long id) throws AccessDeniedException, CloneNotSupportedException {

        Event event = eventDtoConverter.toDomain(eventDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventDtoConverter.toDto(eventService.updateEvent(id, event)));
    }

}