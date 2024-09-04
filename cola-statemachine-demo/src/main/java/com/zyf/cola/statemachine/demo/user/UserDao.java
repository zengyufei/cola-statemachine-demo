package com.zyf.cola.statemachine.demo.user;

import com.zyf.cola.statemachine.demo.entity.UserDTO;

public interface UserDao {
    void updateAuditStatus(String code, String id);

    UserDTO selectById(String id);
}
