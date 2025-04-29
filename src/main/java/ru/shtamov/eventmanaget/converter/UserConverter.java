package ru.shtamov.eventmanaget.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.model.domain.User;
import ru.shtamov.eventmanaget.model.domain.UserRole;
import ru.shtamov.eventmanaget.model.entity.UserEntity;

@Component
public class UserConverter {

    private final EventConverter eventConverter;

    public UserConverter(@Lazy EventConverter eventConverter) {
        this.eventConverter = eventConverter;
    }

    public UserEntity toEntity(User user, String password){
        return new UserEntity(
                user.getLogin(),
                password,
                user.getAge(),
                user.getUserRole().name()
        );
    }

    public User toDomain(UserEntity userEntity){
        return new User(
                userEntity.getId(),
                userEntity.getLogin(),
                userEntity.getAge(),
                UserRole.valueOf(userEntity.getUserRole())
        );
    }
}
