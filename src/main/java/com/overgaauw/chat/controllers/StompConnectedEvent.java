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
public class StompConnectedEvent implements ApplicationListener<SessionSubscribeEvent> {

    private static final Logger log = LoggerFactory.getLogger(StompConnectedEvent.class);

    private final SimpMessagingTemplate template;

    private MessagesRepository messagesRepository;

    @Autowired
    public StompConnectedEvent(SimpMessagingTemplate template, MessagesRepository messagesRepository) {
        this.template = template;
        this.messagesRepository = messagesRepository;
    }

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        log.info(String.format("%s connected", event.getUser().getName()));

        messagesRepository.getMessages().forEach((message) -> {
                template.convertAndSendToUser(
                        event.getUser().getName(), "/secured/user/queue/specific-user", message);
        });
    }
}