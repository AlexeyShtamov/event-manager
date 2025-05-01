package ru.shtamov.eventmanaget.helper;

import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.model.domain.Event;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class KafkaHelper {

    public List<String> getChangedFields(Event oldEvent, Event newEvent) {
        List<String> result = new ArrayList<>();

        if (oldEvent == null || newEvent == null) {
            return result;
        }

        try {
            for (Field field : Event.class.getDeclaredFields()) {
                field.setAccessible(true);
                Object oldValue = field.get(oldEvent);
                Object newValue = field.get(newEvent);

                if (newValue != null && !Objects.equals(oldValue, newValue)) {
                    result.add(String.format("Старый %s: %s, Новый %s: %s",
                            field.getName(), oldValue, field.getName(), newValue));
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Ошибка сравнения объектов", e);
        }

        return result;
    }
}
