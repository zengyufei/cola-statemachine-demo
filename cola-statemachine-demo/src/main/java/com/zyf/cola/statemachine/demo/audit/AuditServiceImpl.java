package com.zyf.cola.statemachine.demo.audit;

import com.zyf.cola.statemachine.demo.audit.machine.AuditContext;
import com.zyf.cola.statemachine.demo.audit.machine.AuditEvent;
import com.zyf.cola.statemachine.demo.audit.machine.AuditState;
import com.zyf.cola.statemachine.demo.base.machine.MachineEnum;
import com.zyf.cola.statemachine.demo.pojo.AuditDTO;
import com.zyf.cola.statemachine.util.StateMachineEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditDao auditDao;

    @Autowired
    private StateMachineEngine stateMachineEngine;

    @Override
    public boolean audit(AuditContext auditContext) {
        Long id = auditContext.getId();
        Integer auditEvent = auditContext.getAuditEvent();

        AuditDTO old = auditDao.selectById(id);
        String oldState = old.getAuditState();
        // 获取当前状态和事件
        AuditState nowState = AuditState.getEnumsByCode(oldState);
        AuditEvent nowEvent = AuditEvent.getEnumsByCode(auditEvent);
        // 执行状态机
        final AuditState newState = stateMachineEngine.fire(MachineEnum.TEST_MACHINE, nowState, nowEvent, auditContext);
        if (nowState == newState) {
            log.info("审核失败!");
            return false;
        } else {
            log.info("审核成功!");
            return true;
        }
    }

    @Override
    public String uml() {
        return stateMachineEngine.generateUml(MachineEnum.TEST_MACHINE);
    }
}
