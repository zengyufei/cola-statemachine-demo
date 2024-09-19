package com.zyf.cola.statemachine.demo.role.dao;

import com.zyf.cola.statemachine.demo.role.entity.RoleDTO;

public interface RoleDao {
    void updateAuditStatus(String roleAuditStateEnumCode, String id);

    RoleDTO selectById(String id);
}
