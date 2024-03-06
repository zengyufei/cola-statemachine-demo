package com.zyf.cola.statemachine.demo.base.machine;

import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.zyf.cola.statemachine.demo.audit.machine.*;
import com.zyf.cola.statemachine.util.StateMachineStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditMachine extends StateMachineStrategy<AuditState, AuditEvent, AuditContext> {

    @Autowired
    private ConditionService conditionService;

    @Autowired
    private ActionService actionService;

    @Override
    public String getMachineType() {
        return MachineEnum.TEST_MACHINE.getCode();
    }

    /**
     * | From(开始状态) | To(抵达状态) | Event(事件) | When(条件)            | Perform(执行动作)  |
     * | -------------- | ------------ | ----------- | --------------------- | ------------------ |
     * | 已申请      | 爸爸同意 | 审核通过    | passOrRejectCondition | passOrRejectAction |
     * | 爸爸同意    | 妈妈同意 | 审核通过    | passOrRejectCondition | passOrRejectAction |
     * | 已申请     | 爸爸不同意 | 审核驳回    | passOrRejectCondition | passOrRejectAction |
     * | 爸爸同意   | 妈妈不同意 | 审核驳回    | passOrRejectCondition | passOrRejectAction |
     * | 已申请    | 已完成状态    | 已完成        | doneCondition        | doneAction        |
     * | 爸爸同意  | 已完成状态    | 已完成        | doneCondition        | doneAction        |
     * | 妈妈同意  | 已完成状态    | 已完成        | doneCondition        | doneAction        |
     */
    @Override
    public void build(StateMachineBuilder<AuditState, AuditEvent, AuditContext> builder) {
        // 已申请->爸爸同意
        builder.externalTransition().from(AuditState.APPLY).to(AuditState.DAD_PASS)
                .on(AuditEvent.PASS)
                .when(conditionService.passOrRejectCondition())
                .perform(actionService.passOrRejectAction());
        // 已申请->爸爸不同意
        builder.externalTransition().from(AuditState.APPLY).to(AuditState.DAD_REJ)
                .on(AuditEvent.REJECT)
                .when(conditionService.passOrRejectCondition())
                .perform(actionService.passOrRejectAction());
        // 爸爸同意->妈妈同意
        builder.externalTransition().from(AuditState.DAD_PASS).to(AuditState.MOM_PASS)
                .on(AuditEvent.PASS)
                .when(conditionService.passOrRejectCondition())
                .perform(actionService.passOrRejectAction());
        // 爸爸同意->妈妈不同意
        builder.externalTransition().from(AuditState.DAD_PASS).to(AuditState.MOM_REJ)
                .on(AuditEvent.REJECT)
                .when(conditionService.passOrRejectCondition())
                .perform(actionService.passOrRejectAction());
        // 已申请->已完成
        // 爸爸同意->已完成
        // 妈妈同意->已完成
        builder.externalTransitions().fromAmong(AuditState.APPLY, AuditState.DAD_PASS, AuditState.MOM_PASS)
                .to(AuditState.DONE)
                .on(AuditEvent.DONE)
                .when(conditionService.doneCondition())
                .perform(actionService.doneAction());
    }

}
