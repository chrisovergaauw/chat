package com.overgaauw.chat.controllers;

import com.overgaauw.chat.data.OutGoingMessage;
import com.overgaauw.chat.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class StompSubscribedEvent implements ApplicationListener<SessionSubscribeEvent>{

    private static final Logger log = LoggerFactory.getLogger(StompSubscribedEvent.class);

    private final SimpMessagingTemplate template;

    private MessagesRepository messagesRepository;

    @Autowired
    public StompSubscribedEvent(SimpMessagingTemplate template, MessagesRepository messagesRepository) {
        this.template = template;
        this.messagesRepository = messagesRepository;
    }

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        log.info("event");
        if ("/secured/chatRoomHistory".equals(event.getMessage().getHeaders().get("simpDestination"))) {

            String username = event.getUser() != null ? event.getUser().getName() : "Unknown";

            messagesRepository.getMessages().forEach((message) -> {
                template.convertAndSendToUser(
                        username, "/secured/user/queue/specific-user", message);
            });

            log.info(String.format("%s connected", username));
            OutGoingMessage outGoingMessage = new OutGoingMessage(
                    "server",
                    String.format("%s has joined the channel!", username));

            template.convertAndSend("/secured/chatRoomHistory", outGoingMessage);
            messagesRepository.insertMessage(outGoingMessage);
        }
    }
}