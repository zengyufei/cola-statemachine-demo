package com.zyf.cola.statemachine.demo.audit;

import com.zyf.cola.statemachine.demo.audit.machine.AuditContext;

public interface AuditService {
    boolean audit(AuditContext auditContext);

    String uml();
}
