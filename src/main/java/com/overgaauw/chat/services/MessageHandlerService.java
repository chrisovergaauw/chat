package com.overgaauw.chat.services;

import com.overgaauw.chat.data.BroadcastingMessage;
import com.overgaauw.chat.data.IncomingMessage;
import com.overgaauw.chat.exceptions.UserExistsException;
import com.overgaauw.chat.repository.MessagesRepository;
import com.overgaauw.chat.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageHandlerService {

    private static final Logger log = LoggerFactory.getLogger(MessageHandlerService.class);

    private final MessagesRepository messagesRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageHandlerService(MessagesRepository messagesRepository, UserRepository userRepository) {
        this.messagesRepository = messagesRepository;
        this.userRepository = userRepository;
    }

    public List<BroadcastingMessage> getEnteredMessageResponse(String name) throws UserExistsException {
        List<BroadcastingMessage> messages;
        if (userRepository.findUserByName(name) == null) {
            userRepository.createUser(name);
            messages = messagesRepository.getMessages();
            messages.add(new BroadcastingMessage("Server",
                    String.format("Welcome %s!", name)));
        }else {
            throw new UserExistsException("A user with this name already exists!");
            // technically this does not do anything yet, user can still send messages with name he provides.
        }
        return messages;
    }

    public BroadcastingMessage registerIncomingMessage(IncomingMessage incomingMessage) {
        BroadcastingMessage msg = new BroadcastingMessage(incomingMessage);
        messagesRepository.insertMessage(msg);
        log.info(msg.toString());
        return msg;
    }
}
