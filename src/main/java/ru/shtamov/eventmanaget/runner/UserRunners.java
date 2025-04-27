package ru.shtamov.eventmanaget.runner;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.shtamov.eventmanaget.model.domain.UserRole;
import ru.shtamov.eventmanaget.model.entity.UserEntity;
import ru.shtamov.eventmanaget.repositorie.UserRepository;

@RequiredArgsConstructor
@Component
public class UserRunners {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${runner.user-login}")
    private String userLogin;
    @Value("${runner.user-password}")
    private String userPassword;

    @Value("${runner.admin-login}")
    private String adminLogin;
    @Value("${runner.admin-password}")
    private String adminPassword;

    @PostConstruct
    @Transactional
    public void run(){
        if (userRepository.count() == 0){
            UserEntity admin = new UserEntity();
            admin.setLogin(adminLogin);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setUserRole(UserRole.ADMIN.name());
            admin.setAge(20);

            UserEntity user = new UserEntity();
            user.setLogin(userLogin);
            user.setPassword(passwordEncoder.encode(userPassword));
            user.setUserRole(UserRole.USER.name());
            user.setAge(20);

            userRepository.save(admin);
            userRepository.save(user);
        }
    }
}
