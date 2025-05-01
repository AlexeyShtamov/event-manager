package ru.shtamov.eventmanaget.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shtamov.eventmanaget.converter.EventConverter;
import ru.shtamov.eventmanaget.model.domain.Event;
import ru.shtamov.eventmanaget.model.domain.EventStatus;
import ru.shtamov.eventmanaget.model.domain.User;
import ru.shtamov.eventmanaget.model.entity.RegistrationEntity;
import ru.shtamov.eventmanaget.repository.EventRepository;
import ru.shtamov.eventmanaget.repository.RegistrationRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final AuthenticationService authenticationService;
    private final EventService eventService;
    private final EventRepository eventRepository;
    private final EventConverter eventConverter;

    @Transactional
    public void registerEvent(Long eventId){
        User userRequest = authenticationService.getCurrentAuthenticatedUserOrThrow();

        Event foundedEvent = eventService.findById(eventId);

        if (registrationRepository.existsByEventIdAndUserId(foundedEvent.getId(), userRequest.getId()))
            throw new IllegalArgumentException("Вы уже записаны на это мероприятие");

        if (!foundedEvent.getStatus().equals(EventStatus.WAIT_START))
            throw new IllegalArgumentException("Мероприятие отменено или уже прошло");

        if(foundedEvent.getMaxPlaces().equals(foundedEvent.getOccupiedPlaces()))
            throw new IllegalArgumentException("Не мест для записи на мероприятие");


        foundedEvent.setOccupiedPlaces(foundedEvent.getOccupiedPlaces() + 1);
        eventRepository.save(eventConverter.toEntity(foundedEvent));
        registrationRepository.save(
                new RegistrationEntity(userRequest.getId(), eventRepository.findById(eventId).orElseThrow()));

        log.info("Регистрация на мероприятие с id {} прошла успешно", eventId);
    }

    @Transactional
    public void canselRegistration(Long eventId) {
        User userRequest = authenticationService.getCurrentAuthenticatedUserOrThrow();

        if (registrationRepository.existsByEventIdAndUserId(userRequest.getId(), eventId))
            throw new IllegalArgumentException("У вас нет регистрации на это мероприятие");

        var registration = registrationRepository.findByEventId(eventId);

        Event foundedEvent = eventService.findById(eventId);

        if (!foundedEvent.getStatus().equals(EventStatus.WAIT_START))
            throw new IllegalArgumentException("Вы не можете убрать регистрацию с мероприятия, которое уже началось");

        foundedEvent.setOccupiedPlaces(foundedEvent.getOccupiedPlaces() - 1);
        eventRepository.save(eventConverter.toEntity(foundedEvent));

        registrationRepository.delete(registration.orElseThrow());

        log.info("Регистрация на мероприятие с id {} отменена", eventId);

    }

    public List<Event> findRegisterEvents(){
        User userRequest = authenticationService.getCurrentAuthenticatedUserOrThrow();

        List<Event> events =
                registrationRepository.findAllByUserId(userRequest.getId()).stream().map(r -> eventConverter.toDomain(r.getEvent())).toList();

        log.info("Найдены все регистрированные мероприятия пользователя с id {}", userRequest.getId());

        return events;
    }
}
