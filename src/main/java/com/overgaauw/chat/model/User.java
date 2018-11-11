package com.overgaauw.chat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "users.getUserByName", query="select u from User u where u.name = :name"),
        @NamedQuery(name = "users.getUserById", query="select u from User u where u.id = :id")
})
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;
    @Column(name = "name", nullable = false, unique = true)
    String name;

    public User(String name) {
        this.name = name;
    }
}
