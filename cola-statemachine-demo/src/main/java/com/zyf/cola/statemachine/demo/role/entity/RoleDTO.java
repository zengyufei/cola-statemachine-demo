package com.zyf.cola.statemachine.demo.role.entity;

import lombok.Data;

@Data
public class RoleDTO {
    String id;
    /**
     * 姓名
     */
    String name;
    /**
     * 审核状态
     */
    String auditState;
}
