package com.zyf.cola.statemachine.demo.user;


import com.zyf.cola.statemachine.demo.params.UserParam;

public interface UserService {
    boolean audit(UserParam userParam);

    String uml();
}
