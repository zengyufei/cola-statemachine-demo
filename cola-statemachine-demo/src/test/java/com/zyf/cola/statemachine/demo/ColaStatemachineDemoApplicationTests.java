package com.zyf.cola.statemachine.demo;

import com.zyf.cola.statemachine.demo.audit.machine.AuditContext;
import com.zyf.cola.statemachine.demo.pojo.AuditParam;
import com.zyf.cola.statemachine.demo.audit.AuditService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ColaStatemachineDemoApplicationTests {

    @Autowired
    private AuditService auditService;

    @Test
    public void getUml() {
        final String uml = auditService.uml();
        System.out.println(uml);
    }

    @Test
    public void babaPass() {
        AuditContext auditContext = new AuditContext();
        auditContext.setId(1L);
        auditContext.setAuditEvent(0);

        auditService.audit(auditContext);
    }

    @Test
    public void mamaPass() {
        AuditContext auditContext = new AuditContext();
        auditContext.setId(2L);
        auditContext.setAuditEvent(0);

        auditService.audit(auditContext);
    }

}
