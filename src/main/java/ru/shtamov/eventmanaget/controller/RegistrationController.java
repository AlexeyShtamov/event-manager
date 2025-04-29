package ru.shtamov.eventmanaget.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtamov.eventmanaget.converter.EventDtoConverter;
import ru.shtamov.eventmanaget.model.dto.EventDto;
import ru.shtamov.eventmanaget.service.RegistrationService;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/events/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final EventDtoConverter eventDtoConverter;

    @PostMapping("/{eventId}")
    public ResponseEntity<Void> registerEvent(@PathVariable Long eventId) throws AccessDeniedException {
        registrationService.registerEvent(eventId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/cancel/{eventId}")
    public ResponseEntity<Void> cancelRegistrationEvent(@PathVariable Long eventId) throws AccessDeniedException {
        registrationService.canselRegistration(eventId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<EventDto>> getRegisteredEvents() {

        List<EventDto> eventDtos = registrationService.findRegisterEvents().stream().map(eventDtoConverter::toDto).toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventDtos);
    }

}
