package ru.shtamov.eventmanaget.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.shtamov.eventmanaget.model.domain.UserRole;
import ru.shtamov.eventmanaget.model.entity.UserEntity;
import ru.shtamov.eventmanaget.repository.UserRepository;

@Slf4j
@EnableConfigurationProperties(RunnerProperties.class)
@RequiredArgsConstructor
@Component
public class UserRunners {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RunnerProperties runnerProperties;


    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void run(){
        log.info("UserRunners начал свою работу");

        if (userRepository.count() == 0){
            UserEntity admin = new UserEntity();
            admin.setLogin(runnerProperties.getAdmin().getLogin());
            admin.setPassword(passwordEncoder.encode(runnerProperties.getAdmin().getPassword()));
            admin.setUserRole(UserRole.ADMIN.name());
            admin.setAge(20);

            UserEntity user = new UserEntity();
            user.setLogin(runnerProperties.getUser().getLogin());
            user.setPassword(passwordEncoder.encode(runnerProperties.getUser().getPassword()));
            user.setUserRole(UserRole.USER.name());
            user.setAge(20);

            userRepository.save(admin);
            userRepository.save(user);
        }
    }
}