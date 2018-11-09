package com.overgaauw.chat.services;

import com.overgaauw.chat.data.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageHandlerService {

    private static final Logger log = LoggerFactory.getLogger(MessageHandlerService.class);


    public Message getEnteredMessageResponse(String name) {
        return new Message("Server",
                String.format("Welcome %s!", name)
        );
    }

    public Message registerIncomingMessage(Message message) {
        log.info(message.toString());
        return message;
    }
}
