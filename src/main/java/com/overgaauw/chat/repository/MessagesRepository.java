package com.overgaauw.chat.repository;

import com.overgaauw.chat.data.BroadcastingMessage;
import com.overgaauw.chat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MessagesRepository {
    private final EntityManager entityManager;

    @Autowired
    public MessagesRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public List<BroadcastingMessage> getMessages() {
        List<Message> results = entityManager.createNamedQuery("messages.getAllMessages", Message.class).getResultList();
        return results.stream().map(msg -> new BroadcastingMessage(msg.getName(), msg.getMessage(), msg.getTimestamp())).collect(Collectors.toList());
    }

    @Transactional
    public void insertMessage(BroadcastingMessage broadcastingMessage) {
        Message msg = new Message(broadcastingMessage);
        entityManager.persist(msg);
    }
}
