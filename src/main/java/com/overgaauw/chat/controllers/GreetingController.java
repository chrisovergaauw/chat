package com.overgaauw.chat.controllers;

import com.overgaauw.chat.data.BroadcastingMessage;
import com.overgaauw.chat.data.UserEnteringMessage;
import com.overgaauw.chat.services.MessageHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Controller
public class GreetingController {

    @Autowired
    MessageHandlerService messageHandlerService;

    @MessageMapping("/entry")
    @SendTo("/topic/lobby")
    public List<BroadcastingMessage> greeting(UserEnteringMessage message) throws Exception {
        return messageHandlerService.getEnteredMessageResponse(HtmlUtils.htmlEscape(message.getName()));
    }
}
