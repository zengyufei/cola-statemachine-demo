package com.zyf.cola.statemachine.demo.role.params;

import com.zyf.cola.statemachine.context.MachineContext;
import lombok.Data;

@Data
public class RoleParam implements MachineContext {
    String id;
    /**
     * 姓名
     */
    String name;
    /**
     * 审核状态
     */
    String auditEvent;

    @Override
    public String getEvent() {
        return auditEvent;
    }
}
