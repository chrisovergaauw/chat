package com.overgaauw.chat.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class IncomingMessage {
    @NonNull
    String name;
    @NonNull
    String message;

    public IncomingMessage(String name, String message) {
        this.name = name;
        this.message = message;
    }
}

