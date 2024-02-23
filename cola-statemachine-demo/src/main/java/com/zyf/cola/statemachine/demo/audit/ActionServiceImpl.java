package com.zyf.cola.statemachine.demo.audit;

import com.alibaba.cola.statemachine.Action;
import com.zyf.cola.statemachine.demo.audit.machine.AuditContext;
import com.zyf.cola.statemachine.demo.audit.machine.AuditEvent;
import com.zyf.cola.statemachine.demo.audit.machine.AuditState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActionServiceImpl implements ActionService {

    @Autowired
    private AuditDao auditDao;

    @Override
    public Action<AuditState, AuditEvent, AuditContext> passOrRejectAction() {
        return (from, to, event, context) -> {
            log.info("passOrRejectAction from {}, to {}, on event {}, id:{}", from, to, event, context.getId());
            auditDao.updateAuditStatus(to.getCode(), context.getId());
        };
    }

    @Override
    public Action<AuditState, AuditEvent, AuditContext> doneAction() {
        return (from, to, event, context) -> {
            log.info("doneAction from {}, to {}, on event {}, id:{}", from, to, event, context.getId());
            auditDao.updateAuditStatus(to.getCode(), context.getId());
        };
    }
}
