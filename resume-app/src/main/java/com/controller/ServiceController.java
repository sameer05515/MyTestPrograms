package com.controller;

import com.pojo.UserPojo;
import com.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("service-controller")
public class ServiceController {
    @Autowired
    LoginService loginService;

    @GetMapping(value = "/users")
    public List<UserPojo> showLoginPage() {
        return  loginService.getAllUsers();
    }
}
