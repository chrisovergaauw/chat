package com.overgaauw.chat.data;

public class EnteringMessage {

    private String name;

    public EnteringMessage() {
    }

    public EnteringMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}