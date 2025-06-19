package com.nrs.consumer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrs.consumer.dto.NotificationDto;
import com.nrs.consumer.email.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationsConsumer {

    private final EmailService emailService;

    public EmailNotificationsConsumer(ObjectMapper objectMapper, EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${email.notifications.kafka.topic}", groupId = "${spring.kafka.group-id}")
    public void listen(final NotificationDto notification) {
        emailService.process(notification);
    }
}
