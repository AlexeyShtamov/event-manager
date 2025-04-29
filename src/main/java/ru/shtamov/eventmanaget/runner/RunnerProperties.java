package ru.shtamov.eventmanaget.runner;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "runner")
@Getter
@Setter
public class RunnerProperties {
    private UserCredentials user;
    private UserCredentials admin;

    @Getter
    @Setter
    public static class UserCredentials {
        private String login;
        private String password;
    }
}