package com.overgaauw.chat.controllers;

import com.overgaauw.chat.data.Greeting;
import com.overgaauw.chat.data.EnteredMessage;
import com.overgaauw.chat.services.MessageHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @Autowired
    MessageHandlerService messageHandlerService;

    @MessageMapping("/entry")
    @SendTo("/topic/greetings")
    public Greeting greeting(EnteredMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return messageHandlerService.getEnteredMessageResponse(HtmlUtils.htmlEscape(message.getName()));
    }
}
