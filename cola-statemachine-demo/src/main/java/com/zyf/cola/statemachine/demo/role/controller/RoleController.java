package com.zyf.cola.statemachine.demo.role.controller;

import com.zyf.cola.statemachine.demo.role.params.RoleParam;
import com.zyf.cola.statemachine.demo.role.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/audit")
    public boolean audit(@RequestBody @Validated RoleParam roleParam) {
        return roleService.audit(roleParam);
    }

    @GetMapping("/uml")
    public String uml() {
        return roleService.uml();
    }
}
