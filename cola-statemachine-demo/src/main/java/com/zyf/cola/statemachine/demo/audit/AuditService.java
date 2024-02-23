package com.zyf.cola.statemachine.demo.audit;

import com.zyf.cola.statemachine.demo.audit.machine.AuditContext;

public interface AuditService {
    void audit(AuditContext auditContext);

    String uml();
}
