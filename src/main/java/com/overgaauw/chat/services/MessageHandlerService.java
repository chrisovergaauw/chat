package com.overgaauw.chat.services;

import com.overgaauw.chat.data.Greeting;
import org.springframework.stereotype.Service;

@Service
public class MessageHandlerService {


    public Greeting getEnteredMessageResponse(String name) {
        return new Greeting(name);
    }
}
