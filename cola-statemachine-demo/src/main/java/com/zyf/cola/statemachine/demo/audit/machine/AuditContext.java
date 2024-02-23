package com.zyf.cola.statemachine.demo.audit.machine;

import lombok.Data;

@Data
public class AuditContext {

    /**
     * id
     */
    private Long id;

    /**
     * 事件
     */
    private Integer auditEvent;

}
