package com.overgaauw.chat.controllers;

import com.overgaauw.chat.data.IncomingMessage;
import com.overgaauw.chat.data.OutGoingMessage;
import com.overgaauw.chat.services.MessageHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    MessageHandlerService messageHandlerService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/secured/chatRoom")
    @SendTo("/secured/chatRoomHistory")
    public OutGoingMessage message(IncomingMessage incomingMessage) throws Exception {
        return messageHandlerService.registerIncomingMessage(incomingMessage);
    }

    @MessageMapping("/secured/room")
    public void sendSpecific(@Payload IncomingMessage incomingMessage) throws Exception {
        OutGoingMessage out = new OutGoingMessage(incomingMessage);
        simpMessagingTemplate.convertAndSendToUser(
               incomingMessage.getTo(), "/secured/user/queue/specific-user", out);
    }
}

