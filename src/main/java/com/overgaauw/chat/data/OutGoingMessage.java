package com.overgaauw.chat.data;

import com.overgaauw.chat.model.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
public class OutGoingMessage extends IncomingMessage {

    String timestamp;

    public OutGoingMessage(String from, String text) {
        super(from, text);
        this.timestamp = new Date().toString();
    }

    public OutGoingMessage(Message DBMessage) {
        setFrom(DBMessage.getName());
        setText(DBMessage.getMessage());
        setTimestamp(DBMessage.getTimestamp());
    }

    public OutGoingMessage(IncomingMessage incomingMessage) {
        super(incomingMessage.getFrom(), incomingMessage.getText());
        this.timestamp = new Date().toString();
    }
}

