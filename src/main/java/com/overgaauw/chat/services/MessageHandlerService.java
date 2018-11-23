package com.overgaauw.chat.services;

import com.overgaauw.chat.data.IncomingMessage;
import com.overgaauw.chat.data.OutGoingMessage;
import com.overgaauw.chat.repository.MessagesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<OutGoingMessage> announceNewUser() {
        log.info("announcing new user...");
        this.simpMessagingTemplate.convertAndSend("/secured/chatRoomHistory",
                new OutGoingMessage("Server", "A user has joined the channel"));
        return messagesRepository.getMessages();
    }

    public void registerPrivateMessage(IncomingMessage incomingMessage) {
        log.info(incomingMessage.toString());
        OutGoingMessage msg = new OutGoingMessage(incomingMessage);
        simpMessagingTemplate.convertAndSendToUser(
                incomingMessage.getTo(), "/secured/user/queue/specific-user", msg);
    }
}
