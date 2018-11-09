package com.overgaauw.chat.data;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class Message {
    @NonNull
    String from;
    @NonNull
    String message;
    String timestamp;

    public Message(String from, String message) {
        this.from = from;
        this.message = message;
        this.timestamp = new Date().toString();
    }

}
