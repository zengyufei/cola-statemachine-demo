package com.zyf.cola.statemachine.demo.audit.machine;

import com.alibaba.cola.statemachine.Condition;
import org.springframework.stereotype.Component;

@Component
public class AuditConditionServiceImpl implements AuditConditionService {
    @Override
    public Condition<AuditContext> passOrRejectCondition() {
        return context -> {
            AuditEvent nowEvent = AuditEvent.getEnumsByCode(context.getAuditEvent());
            System.out.println(nowEvent.getDesc());
            return true;
        };
    }

    @Override
    public Condition<AuditContext> doneCondition() {
        return context -> {
            AuditEvent nowEvent = AuditEvent.getEnumsByCode(context.getAuditEvent());
            System.out.println(nowEvent.getDesc());
            return true;
        };
    }
}
