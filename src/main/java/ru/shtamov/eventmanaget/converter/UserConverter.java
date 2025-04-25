package ru.shtamov.eventmanaget.converter;

import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.model.domain.User;
import ru.shtamov.eventmanaget.model.domain.UserRole;
import ru.shtamov.eventmanaget.model.entity.UserEntity;

@Component
public class UserConverter {

    public UserEntity toEntity(User user){
        return new UserEntity(
                user.getLogin(),
                user.getPassword(),
                user.getAge(),
                user.getUserRole().name()
        );
    }

    public User toDomain(UserEntity userEntity){
        return new User(
                Math.toIntExact(userEntity.getId()),
                userEntity.getLogin(),
                userEntity.getAge(),
                UserRole.valueOf(userEntity.getUserRole())
        );
    }
}
