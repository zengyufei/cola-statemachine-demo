package com.zyf.cola.statemachine.demo.user.controller;

import com.zyf.cola.statemachine.demo.params.UserParam;
import com.zyf.cola.statemachine.demo.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/audit")
    public boolean audit(@RequestBody @Validated UserParam userParam) {
        return userService.audit(userParam);
    }

    @GetMapping("/uml")
    public String uml() {
        return userService.uml();
    }
}
