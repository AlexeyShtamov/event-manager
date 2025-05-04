package ru.shtamov.eventmanaget.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.shtamov.eventmanaget.model.kafka.EventNotification;



@Component
@Slf4j
@RequiredArgsConstructor
public class EventNotificationProducer {

    @Value("${topic-notification}")
    private String notificationTopic;

    private final KafkaTemplate<Long, EventNotification> kafkaTemplate;

    public void eventSent(EventNotification eventNotification){
        kafkaTemplate.send(
                notificationTopic,
                eventNotification.getEventId(),
                eventNotification
        );
        log.info("Уведомление в брокер прошло успешно, сообщение: {}, id измененного мероприятия: ", eventNotification.getEventId());
    }



}
