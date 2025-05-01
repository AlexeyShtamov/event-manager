package ru.shtamov.eventmanaget.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shtamov.eventmanaget.converter.EventConverter;
import ru.shtamov.eventmanaget.helper.KafkaHelper;
import ru.shtamov.eventmanaget.model.domain.*;
import ru.shtamov.eventmanaget.model.entity.EventEntity;
import ru.shtamov.eventmanaget.model.kafka.EventNotification;
import ru.shtamov.eventmanaget.producer.EventNotificationProducer;
import ru.shtamov.eventmanaget.repository.EventRepository;
import ru.shtamov.eventmanaget.repository.RegistrationRepository;

import java.nio.file.AccessDeniedException;
import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class EventService {

    private final EventConverter eventConverter;
    private final EventRepository eventRepository;
    private final LocationService locationService;
    private final AuthenticationService authenticationService;
    private final EventPermissionService eventPermissionService;
    private final EventNotificationProducer eventNotificationProducer;
    private final RegistrationRepository registrationRepository;
    private final KafkaHelper kafkaHelper;


    public Event createEvent(Event event){
        Location location = locationService.findLocation(event.getLocationId());
        User owner = authenticationService.getCurrentAuthenticatedUserOrThrow();

        checkLocationCapacity(location, event);

        event.setOwnerId(owner.getId());
        event.setStatus(EventStatus.WAIT_START);
        event.setEndTime(event.getDate().plusMinutes(event.getDuration()));

        Event createdEvent = eventConverter.toDomain(eventRepository.save(eventConverter.toEntity(event)));

        log.info("Event with name {} is created", createdEvent.getName());
        return createdEvent;
    }

    public Event updateEvent(Long id, Event event) throws AccessDeniedException, CloneNotSupportedException {
        Event foundedEvent = findById(id);

        if (!foundedEvent.getLocationId().equals(event.getLocationId())){
            Location location = locationService.findLocation(event.getLocationId());
            checkLocationCapacity(location, event);
        }

        if (eventPermissionService.canAuthenticatedUserModifyEvent(id))
            throw new AccessDeniedException("Обновить мероприятие может только создатель мероприятия или админ");

        if(event.getMaxPlaces() < foundedEvent.getOccupiedPlaces())
            throw new IllegalArgumentException(("Максимальное количество мест не может быть меньше, чем число занятых мест. " +
                    "Число занятых мест %d. Максимальное кол-во место %d").formatted(foundedEvent.getOccupiedPlaces(), event.getMaxPlaces()));

        Event oldEvent = foundedEvent.clone();

        eventConverter.updateEvent(foundedEvent, event);
        eventRepository.save(eventConverter.toEntity(foundedEvent));

        log.info("Мероприятие с id {} обновлено", id);

        notify(foundedEvent, kafkaHelper.getChangedFields(oldEvent, foundedEvent), id);

        return foundedEvent;
    }

    public List<Event> searchEvents(SearchEvent searchDomain){

        List<EventEntity> events = eventRepository.searchEvents(
                searchDomain.getName(),
                searchDomain.getPlacesMin(),
                searchDomain.getPlacesMax(),
                searchDomain.getDateStartAfter(),
                searchDomain.getDateStartBefore(),
                searchDomain.getCostMin(),
                searchDomain.getContMax(),
                searchDomain.getDurationMin(),
                searchDomain.getDurationMax(),
                searchDomain.getLocationId(),
                searchDomain.getEventStatus()
        );

        log.info("Найден отфильтрованный список мероприятий");

        return events.stream().map(eventConverter::toDomain).toList();

    }

    public List<Event> findCreatedEvents(){

        User userRequest = authenticationService.getCurrentAuthenticatedUserOrThrow();

        List<Event> events = eventRepository.findAllByOwnerId(userRequest.getId())
                .stream().map(eventConverter::toDomain).toList();

        log.info("Найдены все мероприятия, созданные пользователем с id {}", userRequest.getId());

        return events;
    }

    public void deleteEvent(Long id) throws AccessDeniedException {
        Event event = findById(id);

        if (eventPermissionService.canAuthenticatedUserModifyEvent(id)){
            throw new AccessDeniedException("Удалять мероприятие может только создатель мероприятия или админ");
        }


        if(!event.getDate().isAfter(OffsetDateTime.now()))
            throw new IllegalArgumentException("Нельзя отменить мероприятия, которое уже началось");

        event.setStatus(EventStatus.CANCELLED);
        eventRepository.save(eventConverter.toEntity(event));

        notify(event, List.of(String.format("Старый статус: %s, Новый статус: %s"
                , EventStatus.WAIT_START, EventStatus.CANCELLED)), id);

        log.info("Мероприятие с id {} успешно отменено", id);


    }

    public Event findById(Long id) {
        Event foundedEvent = eventConverter.toDomain(
                eventRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Нет мероприятия с id: " + id)));
        log.info("Найдено мероприятие с id {}", id);
        return foundedEvent;
    }

    private void checkLocationCapacity(Location location, Event event){
        if (location.getCapacity() < event.getMaxPlaces())
            throw new IllegalArgumentException(("Локация не позволяет вместить столько участников. " +
                "Макс. мест локации: %d, макс. мест мероприятия: %d").formatted(location.getCapacity(), event.getMaxPlaces()));
    }

    private void notify(Event event, List<String> changedFields, Long id) {
        eventNotificationProducer.eventSent(new EventNotification(
                event.getId(),
                eventPermissionService.getAuthenticatedUserId(),
                event.getOwnerId(),
                changedFields,
                registrationRepository.findAllUserLoginByEventRegisterIdQuery(id)
        ));
    }
}
