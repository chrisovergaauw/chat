package com.overgaauw.chat.controllers;

import com.overgaauw.chat.data.IncomingMessage;
import com.overgaauw.chat.data.OutGoingMessage;
import com.overgaauw.chat.services.MessageHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    MessageHandlerService messageHandlerService;

    @MessageMapping("/secured/chatRoom")
    @SendTo("/secured/chatRoomHistory")
    public OutGoingMessage message(IncomingMessage incomingMessage) {
        return messageHandlerService.registerIncomingMessage(incomingMessage);
    }

    @MessageMapping("/secured/room")
    public void sendSpecific(@Payload IncomingMessage incomingMessage) {
        messageHandlerService.registerPrivateMessage(incomingMessage);
    }
}

