package ru.shtamov.eventmanaget.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.shtamov.eventmanaget.model.domain.User;
import ru.shtamov.eventmanaget.model.domain.UserRole;
import ru.shtamov.eventmanaget.model.entity.EventEntity;
import ru.shtamov.eventmanaget.repository.EventRepository;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class EventPermissionService {

    private final EventRepository eventRepository;

    public boolean canAuthenticatedUserModifyEvent(Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().
                getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NoSuchElementException("Ошибка аутентификации");
        }
        User currentUser = (User) authentication.getPrincipal();
        EventEntity event = eventRepository.findById(eventId).orElse(null);

        if (event == null) {
            throw new NoSuchElementException("Ошибка аутентификации");
        }
        return (event.getOwnerId().equals(currentUser.getId()) || currentUser.getUserRole().equals(UserRole.ADMIN));
    }

}
