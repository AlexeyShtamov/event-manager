package ru.shtamov.eventmanaget.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shtamov.eventmanaget.converter.UserConverter;
import ru.shtamov.eventmanaget.model.domain.User;
import ru.shtamov.eventmanaget.model.entity.UserEntity;
import ru.shtamov.eventmanaget.repositorie.UserRepository;

import java.util.NoSuchElementException;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;


    public User findByLogin(String login){
        UserEntity userEntity = userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Нет пользователя с логином: " + login));

        log.info("Найден пользователь с логином: {}", userEntity.getLogin());

        return userConverter.toDomain(userEntity);
    }

    public User findById(Integer id){
        User foundedUser = userConverter
                .toDomain(
                        userRepository.findById(Long.valueOf(id))
                                .orElseThrow(() -> new NoSuchElementException("Нет пользователя с id: " + id))
                );

        log.info("Найден пользователь с id {}", id);
        return foundedUser;
    }
}
