package com.zyf.cola.statemachine.demo.pojo;

import lombok.Data;

@Data
public class AuditParam {

    /**
     * id
     */
    private Long id;

    /**
     * 事件
     */
    private Integer auditEvent;
}
