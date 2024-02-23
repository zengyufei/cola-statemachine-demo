package com.zyf.cola.statemachine.demo.audit;

import com.zyf.cola.statemachine.demo.audit.machine.AuditContext;
import com.zyf.cola.statemachine.demo.pojo.AuditParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private AuditService auditService;

    @PostMapping("/audit")
    public void audit(@RequestBody @Validated AuditParam auditParam) {
        AuditContext auditContext = new AuditContext();
        BeanUtils.copyProperties(auditParam, auditContext);
        auditService.audit(auditContext);
    }

    @GetMapping("/uml")
    public String uml() {
        return auditService.uml();
    }
}
