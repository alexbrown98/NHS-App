package com.example.demo.controllers;

import com.example.demo.exceptions.UserAlreadyExistException;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public void register(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String passwordConfirmation) {
        try {
            userService.register(username, password, passwordConfirmation);
        } catch (UserAlreadyExistException e) {
            //Todo : return appropriate failure response
            e.printStackTrace();
        }
    }
}
