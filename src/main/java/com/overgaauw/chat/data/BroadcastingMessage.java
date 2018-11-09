package com.overgaauw.chat.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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

    public BroadcastingMessage(IncomingMessage incomingMessage) {
        this.name = incomingMessage.name;
        this.message = incomingMessage.message;
        this.timestamp = new Date().toString();
    }
}
