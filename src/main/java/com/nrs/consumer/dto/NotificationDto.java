package com.nrs.consumer.dto;

public class NotificationDto {
    private String type; // Create enum for this
    private String receiver;
    private String message;

    public NotificationDto() {
    }

    public NotificationDto(String type, String receiver, String message) {
        this.type = type;
        this.receiver = receiver;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
