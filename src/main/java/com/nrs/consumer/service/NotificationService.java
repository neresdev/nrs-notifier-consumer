package com.nrs.consumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrs.consumer.dto.NotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class.getName());
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public NotificationService(ObjectMapper objectMapper, EmailService emailService) {
        this.objectMapper = objectMapper;
        this.emailService = emailService;
    }

    public void consume(final NotificationDto notification) {
        try {
            System.out.println("notification received: \n" + objectMapper.writeValueAsString(notification));
            emailService.send(notification);
        } catch (final JsonProcessingException e) {
            log.error("Error while parse message {}", e.getMessage(), e);
        }
    }
}
