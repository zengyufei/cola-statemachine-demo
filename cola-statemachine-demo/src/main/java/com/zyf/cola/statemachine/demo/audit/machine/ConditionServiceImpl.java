package com.zyf.cola.statemachine.demo.audit.machine;

import com.alibaba.cola.statemachine.Condition;
import org.springframework.stereotype.Component;

@Component
public class ConditionServiceImpl implements ConditionService {
    @Override
    public Condition<AuditContext> passOrRejectCondition() {
        return context -> {
            System.out.println(1);
            return true;
        };
    }

    @Override
    public Condition<AuditContext> doneCondition() {
        return context -> {
            System.out.println(1);
            return true;
        };
    }
}
