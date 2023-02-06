package com.controller;

import com.pojo.UserPojo;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("service-controller")
public class ServiceController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/users")
    public List<UserPojo> getAll() {
        return  userService.getAllUsers();
    }

    @GetMapping(value = "/users/{id}")
    public UserPojo getById(@PathVariable int id) {
        return  userService.getUserById(id);
    }
}
