package com.overgaauw.chat.controllers;

import com.overgaauw.chat.data.BroadcastingMessage;
import com.overgaauw.chat.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class StompConnectedEvent implements ApplicationListener<SessionConnectedEvent> {

    private static final Logger log = LoggerFactory.getLogger(StompConnectedEvent.class);

    private final SimpMessagingTemplate template;

    private MessagesRepository messagesRepository;

    @Autowired
    public StompConnectedEvent(SimpMessagingTemplate template, MessagesRepository messagesRepository) {
        this.template = template;
        this.messagesRepository = messagesRepository;
    }

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        this.template.convertAndSend("/topic/lobby",
                new BroadcastingMessage("Server", "A user has joined the channel"));
        messagesRepository.getMessages().forEach((message) -> this.template.convertAndSend("/topic/lobby", message));
    }
}