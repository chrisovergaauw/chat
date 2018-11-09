package com.overgaauw.chat.data;

public class Greeting {

    private String content;

    public Greeting() {
    }

    public Greeting(String name) {
        this.content = String.format("Welcome, %s!", name);
    }

    public String getContent() {
        return content;
    }

}
