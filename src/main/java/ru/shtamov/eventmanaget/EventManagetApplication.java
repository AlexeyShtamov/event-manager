package ru.shtamov.eventmanaget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventManagetApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagetApplication.class, args);
    }

}
