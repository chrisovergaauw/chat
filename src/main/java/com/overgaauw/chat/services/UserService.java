package com.overgaauw.chat.services;

import com.overgaauw.chat.data.IncomingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private MessageHandlerService messageHandlerService;

    public List<String> getAllOnlineUsers() {
        final List<UserDetails> allPrincipals = (List<UserDetails>)(Object) sessionRegistry.getAllPrincipals();
        return allPrincipals.stream().map(UserDetails::getUsername).distinct().collect(Collectors.toList());
    }

    @Async
    @EventListener({AuthenticationSuccessEvent.class, HttpSessionDestroyedEvent.class })
    public void onHttpSessionCreatedEvent() throws InterruptedException {
        log.debug("session event");
        Thread.sleep(1000);
        IncomingMessage msg = new IncomingMessage("server", "userlist changed");
        for (String user : getAllOnlineUsers()) {
            msg.setTo(user);
            messageHandlerService.registerSystemMessage(msg);
        }
    }
}
