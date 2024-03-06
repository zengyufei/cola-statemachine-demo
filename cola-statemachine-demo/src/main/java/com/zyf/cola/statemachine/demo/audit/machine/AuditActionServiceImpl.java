package com.zyf.cola.statemachine.demo.audit.machine;

import com.alibaba.cola.statemachine.Action;
import com.zyf.cola.statemachine.demo.audit.AuditDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuditActionServiceImpl implements AuditActionService {

    @Autowired
    private AuditDao auditDao;

    @Override
    public Action<AuditState, AuditEvent, AuditContext> passOrRejectAction() {
        return (from, to, event, context) -> {
            log.info("通过或拒绝动作 从 [{}] 状态, 变更为 [{}], 因为 [{}] 事件, id: [{}]", from.getDesc(), to.getDesc(), event.getDesc(), context.getId());
            auditDao.updateAuditStatus(to.getCode(), context.getId());
        };
    }

    @Override
    public Action<AuditState, AuditEvent, AuditContext> doneAction() {
        return (from, to, event, context) -> {
            log.info("完成动作 从 [{}] 状态, 变更为 [{}], 因为 [{}] 事件, id: [{}]", from.getDesc(), to.getDesc(), event.getDesc(), context.getId());
            auditDao.updateAuditStatus(to.getCode(), context.getId());
        };
    }
}
