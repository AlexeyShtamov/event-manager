package ru.shtamov.eventmanaget.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.model.domain.Event;
import ru.shtamov.eventmanaget.model.domain.EventStatus;
import ru.shtamov.eventmanaget.model.kafka.EventNotification;

import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class EventNotificationProducer {

    @Value("${topic-notification}")
    private String notificationTopic;

    private final KafkaTemplate<Long, EventNotification> kafkaTemplate;

    public void eventSent(EventNotification eventNotification){
        var result = kafkaTemplate.send(
                notificationTopic,
                eventNotification.eventId(),
                eventNotification
        );
    }



}
