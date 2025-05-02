package ru.shtamov.eventmanaget.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.shtamov.eventmanaget.model.domain.User;
import ru.shtamov.eventmanaget.model.domain.UserRole;
import ru.shtamov.eventmanaget.model.entity.EventEntity;
import ru.shtamov.eventmanaget.repository.EventRepository;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventPermissionService {

    private final EventRepository eventRepository;

    public boolean canAuthenticatedUserModifyEvent(Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().
                getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NoSuchElementException("Ошибка аутентификации 1");
        }
        User currentUser = (User) authentication.getPrincipal();
        EventEntity event = eventRepository.findById(eventId).orElse(null);

        if (event == null) {
            throw new NoSuchElementException("Ошибка аутентификации 2");
        }

        log.info("Пользователю с id {} разрешено выполнять операцию", currentUser.getId());
        return (event.getOwnerId().equals(currentUser.getId()) || currentUser.getUserRole().equals(UserRole.ADMIN));
    }

    public Long getAuthenticatedUserId(){
        Authentication authentication = SecurityContextHolder.getContext().
                getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NoSuchElementException("Ошибка аутентификации");
        }

        User currentUser = (User) authentication.getPrincipal();
        log.info("Получен id пользователя {}", currentUser.getLogin());
        return currentUser.getId();
    }

}
