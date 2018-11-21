package com.overgaauw.chat.data;

import com.overgaauw.chat.model.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
public class OutGoingMessage extends IncomingMessage {

    String timestamp;

    public OutGoingMessage(String from, String text) {
        super(from, text);
        this.timestamp = formatDate(new Date());
    }

    public OutGoingMessage(Message DBMessage) {
        setFrom(DBMessage.getName());
        setText(DBMessage.getMessage());
        setTimestamp(DBMessage.getTimestamp());
    }

    public OutGoingMessage(IncomingMessage incomingMessage) {
        super(incomingMessage.getFrom(), incomingMessage.getText());
        this.timestamp = formatDate(new Date());
    }

    private String formatDate(Date date) {
        String pattern = "yy-MM-dd HH:MM:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}

