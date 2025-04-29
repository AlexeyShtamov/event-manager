package ru.shtamov.eventmanaget.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shtamov.eventmanaget.service.UserService;
import ru.shtamov.eventmanaget.converter.UserDtoConverter;
import ru.shtamov.eventmanaget.model.dto.UserDto;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDtoConverter userDtoConverter;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDtoConverter.toDto(userService.findById(id)));
    }
}
