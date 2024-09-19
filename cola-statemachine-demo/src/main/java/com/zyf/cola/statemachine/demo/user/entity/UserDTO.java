package com.zyf.cola.statemachine.demo.user.entity;

import lombok.Data;

@Data
public class UserDTO {
    String id;
    /**
     * 身份证
     */
    String idCard;
    /**
     * 审核状态
     */
    String auditState;
}
