package com.nrs.consumer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrs.consumer.dto.NotificationDto;
import com.nrs.consumer.email.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;


@Component
public class MessageConsumer {

    private final EmailService emailService;

    public MessageConsumer(ObjectMapper objectMapper, EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${notification}")
    public void listen(final NotificationDto notification) {
        emailService.process(notification);
    }
}
