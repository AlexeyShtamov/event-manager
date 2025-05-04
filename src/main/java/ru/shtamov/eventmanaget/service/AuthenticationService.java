package ru.shtamov.eventmanaget.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shtamov.eventmanaget.converter.UserConverter;
import ru.shtamov.eventmanaget.model.domain.User;
import ru.shtamov.eventmanaget.config.security.jwt.JwtTokenManager;
import ru.shtamov.eventmanaget.exception.IsAlreadyExistException;
import ru.shtamov.eventmanaget.model.entity.UserEntity;
import ru.shtamov.eventmanaget.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenManager jwtTokenManager;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(User user, String password){
        if (userRepository.existsByLogin(user.getLogin()))
            throw new IsAlreadyExistException("Пользователь с логином %s уже существует".formatted(user.getLogin()));

        UserEntity createdUserEntity = userRepository.save(userConverter.toEntity(user, passwordEncoder.encode(password)));
        User createdUser = userConverter.toDomain(createdUserEntity);

        log.info("Пользователь с логином {} создан", createdUser.getLogin());

        return  createdUser;
    }

    public String authenticateUser(String login, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login,
                        password
                )
        );
        ;
        return jwtTokenManager.generateToken(login, authentication.getAuthorities());
    }

    public User getCurrentAuthenticatedUserOrThrow() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Пользователь не аутентифицирован");
        }
        return (User) authentication.getPrincipal();
    }



}
