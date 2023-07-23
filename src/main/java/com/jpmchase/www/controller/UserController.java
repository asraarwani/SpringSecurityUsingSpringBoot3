package com.jpmchase.www.controller;

import com.jpmchase.www.entity.User;
import com.jpmchase.www.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private ApplicationUserService applicationUserService;

    @PostMapping("/users")
    public String addUser(@RequestBody User applicationUser) {
        return applicationUserService.saveUser(applicationUser);
    }
}
