package com.overgaauw.chat.services;

import com.overgaauw.chat.data.IncomingMessage;
import com.overgaauw.chat.data.OutGoingMessage;
import com.overgaauw.chat.repository.MessagesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageHandlerService {

    private static final Logger log = LoggerFactory.getLogger(MessageHandlerService.class);

    private final MessagesRepository messagesRepository;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public MessageHandlerService(MessagesRepository messagesRepository,
                                 SimpMessagingTemplate simpMessagingTemplate) {
        this.messagesRepository = messagesRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public OutGoingMessage registerIncomingMessage(IncomingMessage incomingMessage) {
        OutGoingMessage msg = new OutGoingMessage(incomingMessage.getFrom(), incomingMessage.getText());
        messagesRepository.insertMessage(msg);
        log.info(msg.toString());
        return msg;
    }

    public void registerPrivateMessage(IncomingMessage incomingMessage) {
        registerSpecificMessage(incomingMessage, "chat");
    }
    public void registerSystemMessage(IncomingMessage incomingMessage) {
        registerSpecificMessage(incomingMessage, "system");
    }


    private void registerSpecificMessage(IncomingMessage incomingMessage, String destination) {
        log.info(incomingMessage.toString());
        OutGoingMessage msg = new OutGoingMessage(incomingMessage);
        simpMessagingTemplate.convertAndSendToUser(
                incomingMessage.getTo(), String.format("/secured/user/queue/%s/specific-user", destination), msg);
    }
}
