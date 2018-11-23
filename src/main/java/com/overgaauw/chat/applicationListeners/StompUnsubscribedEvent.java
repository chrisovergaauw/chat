package com.overgaauw.chat.applicationListeners;

import com.overgaauw.chat.data.OutGoingMessage;
import com.overgaauw.chat.repository.MessagesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Component
public class StompUnsubscribedEvent implements ApplicationListener<SessionUnsubscribeEvent> {

    private static final Logger log = LoggerFactory.getLogger(StompUnsubscribedEvent.class);

    private final SimpMessagingTemplate template;

    private MessagesRepository messagesRepository;

    @Autowired
    public StompUnsubscribedEvent(SimpMessagingTemplate template, MessagesRepository messagesRepository) {
        this.template = template;
        this.messagesRepository = messagesRepository;
    }

    @Override
    public void onApplicationEvent(SessionUnsubscribeEvent event) {
        if ("/secured/chatRoomHistory".equals(event.getMessage().getHeaders().get("simpDestination"))) {

            String username = event.getUser() != null ? event.getUser().getName() : "Someone";

            OutGoingMessage outGoingMessage = new OutGoingMessage(
                    "server", String.format("%s has left the channel!", username));

            template.convertAndSend("/secured/chatRoomHistory", outGoingMessage);
            messagesRepository.insertMessage(outGoingMessage);
        }
    }
}