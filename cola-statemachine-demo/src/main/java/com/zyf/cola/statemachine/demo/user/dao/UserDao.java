package com.zyf.cola.statemachine.demo.user.dao;


import com.zyf.cola.statemachine.demo.user.entity.UserDTO;

public interface UserDao {
    void updateAuditStatus(String userAuditStateEnumCode, String id);

    UserDTO selectById(String id);
}
