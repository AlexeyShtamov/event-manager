package ru.shtamov.eventmanaget.converter;

import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.model.domain.User;
import ru.shtamov.eventmanaget.model.domain.UserRole;
import ru.shtamov.eventmanaget.model.dto.RegisterUserDto;
import ru.shtamov.eventmanaget.model.dto.UserDto;

@Component
public class UserDtoConverter {

    public UserDto toDto(User user){
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getAge(),
                user.getUserRole().name()
        );
    }

    public User toDomain(RegisterUserDto userDto) {
        return new User(
                userDto.login(),
                userDto.age(),
                UserRole.USER
        );
    }
}
