package com.overgaauw.chat.model;

import com.overgaauw.chat.data.BroadcastingMessage;
import io.micrometer.core.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "messages")
@NamedQueries({
        @NamedQuery(name = "messages.getAllMessages", query = "SELECT m FROM Message m")
})
public class Message implements Serializable {

    @Id
    @Column(name = "name")
    private String name;
    @Id
    @NonNull
    @Column(name= "message")
    private String message;
    @Column(name = "timestamp")
    private String timestamp;

    public Message(BroadcastingMessage broadcastingMessage) {
        this.name = broadcastingMessage.getName();
        this.message = broadcastingMessage.getMessage();
        this.timestamp = broadcastingMessage.getTimestamp();
    }
}
