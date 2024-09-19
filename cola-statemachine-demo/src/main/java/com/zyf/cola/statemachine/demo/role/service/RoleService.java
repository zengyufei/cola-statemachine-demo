package com.zyf.cola.statemachine.demo.role.service;

import com.zyf.cola.statemachine.demo.role.params.RoleParam;

public interface RoleService {
    boolean audit(RoleParam roleParam);

    String uml();
}
