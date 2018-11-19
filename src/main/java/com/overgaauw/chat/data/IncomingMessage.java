package com.overgaauw.chat.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class IncomingMessage {

    @NonNull
    String from;
    String to;
    @NonNull
    String text;

    public IncomingMessage(String from, String text) {
        this.from = from;
        this.text = text;
    }
}

