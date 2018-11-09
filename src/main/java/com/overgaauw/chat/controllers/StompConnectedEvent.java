package com.overgaauw.chat.controllers;

import com.overgaauw.chat.data.BroadcastingMessage;
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

    @Autowired
    public StompConnectedEvent(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        log.debug("Client connected.");
        this.template.convertAndSend("/topic/lobby",
                new BroadcastingMessage("Server", "A User has joined the channel"));
    }
}