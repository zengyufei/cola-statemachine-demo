package com.zyf.cola.statemachine.demo.audit.machine;

import com.zyf.cola.statemachine.demo.audit.AuditDao;
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
    public void audit(AuditContext auditContext) {
        Long id = auditContext.getId();
        AuditDTO auditDTO = auditDao.selectById(id);
        String auditState = auditDTO.getAuditState();
        Integer auditEvent = auditContext.getAuditEvent();
        // 获取当前状态和事件
        AuditState nowState = AuditState.getEnumsByCode(auditState);
        AuditEvent nowEvent = AuditEvent.getEnumsByCode(auditEvent);
        // 执行状态机
        stateMachineEngine.fire(MachineEnum.TEST_MACHINE, nowState, nowEvent, auditContext);
    }

    @Override
    public String uml() {
        return stateMachineEngine.generateUml(MachineEnum.TEST_MACHINE);
    }
}
