package com.zyf.cola.statemachine.demo.user.machine;

import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.zyf.cola.statemachine.demo.base.MachineType;
import com.zyf.cola.statemachine.demo.params.UserParam;
import com.zyf.cola.statemachine.service.MachineActionService;
import com.zyf.cola.statemachine.service.MachineConditionService;
import com.zyf.cola.statemachine.util.StateMachineStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMachine extends StateMachineStrategy<UserAuditState, UserAuditEvent, UserParam> {

    @Autowired
    private MachineConditionService<UserParam> machineConditionService;

    @Autowired
    private MachineActionService<UserAuditState, UserAuditEvent, UserParam> machineActionService;

    @Override
    public String getMachineType() {
        return MachineType.USER_MACHINE.getCode();
    }

    /**
     * | From(开始状态) | To(抵达状态) | Event(事件)  |
     * | -------------- | ------------ | -----------  |
     * | 已申请      | 爸爸同意 | 审核通过    |
     * | 爸爸同意    | 妈妈同意 | 审核通过     |
     * | 已申请     | 爸爸不同意 | 审核驳回    |
     * | 爸爸同意   | 妈妈不同意 | 审核驳回    |
     * | 已申请    | 已完成状态    | 已完成    |
     * | 爸爸同意  | 已完成状态    | 已完成    |
     * | 妈妈同意  | 已完成状态    | 已完成    |
     */
    @Override
    public void build(StateMachineBuilder<UserAuditState, UserAuditEvent, UserParam> builder) {
        // 已申请->爸爸同意
        builder.externalTransition().from(UserAuditState.APPLY).to(UserAuditState.DAD_PASS)
                .on(UserAuditEvent.PASS)
                .when(machineConditionService.condition())
                .perform(machineActionService.action());
        // 已申请->爸爸不同意
        builder.externalTransition().from(UserAuditState.APPLY).to(UserAuditState.DAD_REJ)
                .on(UserAuditEvent.REJECT)
                .when(machineConditionService.condition())
                .perform(machineActionService.action());
        // 爸爸同意->妈妈同意
        builder.externalTransition().from(UserAuditState.DAD_PASS).to(UserAuditState.MOM_PASS)
                .on(UserAuditEvent.PASS)
                .when(machineConditionService.condition())
                .perform(machineActionService.action());
        // 爸爸同意->妈妈不同意
        builder.externalTransition().from(UserAuditState.DAD_PASS).to(UserAuditState.MOM_REJ)
                .on(UserAuditEvent.REJECT)
                .when(machineConditionService.condition())
                .perform(machineActionService.action());
        // 已申请->已完成
        // 爸爸同意->已完成
        // 妈妈同意->已完成
        builder.externalTransitions().fromAmong(UserAuditState.APPLY, UserAuditState.DAD_PASS, UserAuditState.MOM_PASS)
                .to(UserAuditState.DONE)
                .on(UserAuditEvent.DONE)
                .when(machineConditionService.condition())
                .perform(machineActionService.action());
    }

}
