package com.overgaauw.chat.services;

import com.overgaauw.chat.data.BroadcastingMessage;
import com.overgaauw.chat.data.IncomingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageHandlerService {

    private static final Logger log = LoggerFactory.getLogger(MessageHandlerService.class);


    public BroadcastingMessage getEnteredMessageResponse(String name) {
        return new BroadcastingMessage("Server",
                String.format("Welcome %s!", name)
        );
    }

    public BroadcastingMessage registerIncomingMessage(IncomingMessage incomingMessage) {
        BroadcastingMessage msg = new BroadcastingMessage(incomingMessage);
        log.info(msg.toString());
        return msg;
    }
}
