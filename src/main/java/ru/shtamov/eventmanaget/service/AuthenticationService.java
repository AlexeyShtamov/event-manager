package ru.shtamov.eventmanaget.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shtamov.eventmanaget.converter.UserConverter;
import ru.shtamov.eventmanaget.model.domain.User;
import ru.shtamov.eventmanaget.model.domain.UserRole;
import ru.shtamov.eventmanaget.config.security.jwt.JwtTokenManager;
import ru.shtamov.eventmanaget.exception.IsAlreadyExistException;
import ru.shtamov.eventmanaget.model.entity.UserEntity;
import ru.shtamov.eventmanaget.repositorie.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenManager jwtTokenManager;

    public User registerUser(String login, String password, Integer age){
        if (userRepository.existsByLogin(login))
            throw new IsAlreadyExistException("Пользователь с логином %s уже существует".formatted(login));

        UserEntity userEntity = new UserEntity(
                login,
                passwordEncoder.encode(password),
                age,
                UserRole.USER.name()
        );

        log.info("Пользователь с логином {} создан", userEntity.getLogin());

        return  userConverter.toDomain(userEntity);
    }

    public String authenticateUser(String login, String password){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login,
                        password
                )
        );
        return jwtTokenManager.generateToken(login);
    }

    public User getCurrentAuthenticatedUserOrThrow() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Пользователь не аутентифицирован");
        }
        return (User) authentication.getPrincipal();
    }



}
