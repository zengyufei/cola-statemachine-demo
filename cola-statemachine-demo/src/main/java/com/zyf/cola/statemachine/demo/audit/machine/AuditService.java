package com.zyf.cola.statemachine.demo.audit.machine;

public interface AuditService {
    void audit(AuditContext auditContext);

    String uml();
}
