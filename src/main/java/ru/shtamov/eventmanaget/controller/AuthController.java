package ru.shtamov.eventmanaget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shtamov.eventmanaget.model.domain.User;
import ru.shtamov.eventmanaget.model.dto.EnterUserDto;
import ru.shtamov.eventmanaget.service.AuthenticationService;
import ru.shtamov.eventmanaget.converter.UserDtoConverter;
import ru.shtamov.eventmanaget.model.dto.JwtResponse;
import ru.shtamov.eventmanaget.model.dto.RegisterUserDto;
import ru.shtamov.eventmanaget.model.dto.UserDto;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid RegisterUserDto userDto){
        User createdUser = authenticationService.registerUser(userDtoConverter.toDomain(userDto), userDto.password());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userDtoConverter.toDto(createdUser));
    }

    @PostMapping("/users/auth")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody @Valid EnterUserDto userDto){
        String jwt = authenticationService.authenticateUser(userDto.login(), userDto.password());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new JwtResponse(jwt));
    }

}
