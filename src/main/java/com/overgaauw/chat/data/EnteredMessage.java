package com.overgaauw.chat.data;

public class EnteredMessage {

    private String name;

    public EnteredMessage() {
    }

    public EnteredMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}