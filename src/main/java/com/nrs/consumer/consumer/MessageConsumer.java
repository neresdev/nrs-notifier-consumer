package com.nrs.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrs.consumer.dto.NotificationDto;
import com.nrs.consumer.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class MessageConsumer {
    private final NotificationService service;

    public MessageConsumer(ObjectMapper objectMapper, NotificationService service) {
        this.service = service;
    }

    @KafkaListener(topics = "notifications", groupId = "group-1")
    public void listen(final NotificationDto notification) {
        service.consume(notification);
    }
}
