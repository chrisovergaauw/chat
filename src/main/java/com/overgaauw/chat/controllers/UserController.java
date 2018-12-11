package com.overgaauw.chat.controllers;

import com.overgaauw.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping("/secured/trackedUsers")
    public List<String> getListLoggedInUsers() {
        return userService.getAllOnlineUsers();
    }
}
