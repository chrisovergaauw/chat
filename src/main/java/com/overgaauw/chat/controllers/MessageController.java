package com.overgaauw.chat.controllers;

import com.overgaauw.chat.data.BroadcastingMessage;
import com.overgaauw.chat.data.IncomingMessage;
import com.overgaauw.chat.services.MessageHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    MessageHandlerService messageHandlerService;

    @MessageMapping("/lobby")
    @SendTo("/topic/lobby")
    public BroadcastingMessage message(IncomingMessage incomingMessage) throws Exception {
        return messageHandlerService.registerIncomingMessage(incomingMessage);
    }
}

