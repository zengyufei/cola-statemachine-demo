package com.zyf.cola.statemachine.demo.params;

import com.zyf.cola.statemachine.context.MachineContext;
import com.zyf.cola.statemachine.demo.user.machine.UserAuditEvent;
import lombok.Data;

@Data
public class UserParam implements MachineContext {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 事件
     */
    private UserAuditEvent userAuditEvent;

    @Override
    public String getId() {
        return userId;
    }

    @Override
    public String getEvent() {
        return userAuditEvent.getCode();
    }
}
