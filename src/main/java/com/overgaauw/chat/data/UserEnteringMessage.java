package com.overgaauw.chat.data;

public class UserEnteringMessage {

    private String name;

    public UserEnteringMessage() {
    }

    public UserEnteringMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}