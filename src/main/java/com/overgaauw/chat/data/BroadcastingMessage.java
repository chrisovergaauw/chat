package com.overgaauw.chat.data;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class BroadcastingMessage {
    @NonNull
    String name;
    @NonNull
    String message;
    String timestamp;

    public BroadcastingMessage(String name, String message) {
        this.name = name;
        this.message = message;
        this.timestamp = new Date().toString();
    }

    public BroadcastingMessage(String name, String message, String timestamp) {
        this.name = name;
        this.message = message;
        this.timestamp = timestamp;
    }

    public BroadcastingMessage(IncomingMessage incomingMessage) {
        this.name = incomingMessage.name;
        this.message = incomingMessage.message;
        this.timestamp = new Date().toString();
    }

    @Override
    public String toString(){
        return String.format("{ \"name\": \"%s\", " +
                "\"message\": \"%s\", " +
                "\"timestamp\": \"%s\" }",
                name, message, timestamp);
    }
}
