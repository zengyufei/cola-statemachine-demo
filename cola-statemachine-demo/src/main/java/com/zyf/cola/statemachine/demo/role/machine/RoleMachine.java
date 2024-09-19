package com.zyf.cola.statemachine.demo.role.machine;

import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.zyf.cola.statemachine.demo.base.MachineType;
import com.zyf.cola.statemachine.demo.role.params.RoleParam;
import com.zyf.cola.statemachine.service.MachineActionService;
import com.zyf.cola.statemachine.service.MachineConditionService;
import com.zyf.cola.statemachine.util.StateMachineStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RoleMachine extends StateMachineStrategy<RoleAuditState, RoleAuditEvent, RoleParam> {

    @Autowired
    private MachineConditionService<RoleParam> machineConditionService;

    @Autowired
    private MachineActionService<RoleAuditState, RoleAuditEvent, RoleParam> machineActionService;

    @Override
    public String getMachineType() {
        return MachineType.ROLE_MACHINE.getCode();
    }

    /**
     * | From(开始状态) | To(抵达状态) | Event(事件)  |
     * | -------------- | ------------ | -----------  |
     * | 已申请入帮      | 坐卿同意 | 同意    |
     * | 坐卿同意    | 帮主同意 | 同意     |
     * | 帮主同意    | 入帮完成 | 一言堂     |
     * | 已申请入帮     | 坐卿不同意 | 不同意    |
     * | 坐卿同意   | 帮主不同意 | 不同意    |
     * | 已申请入帮   | 一言堂不同意 | 不同意    |
     * | 坐卿同意   | 一言堂不同意 | 不同意    |
     * | 帮主同意   | 一言堂不同意 | 不同意    |
     * | 已申请入帮    | 入帮完成    | 一言堂    |
     * | 坐卿同意    | 入帮完成    | 一言堂    |
     */
    @Override
    public void build(StateMachineBuilder<RoleAuditState, RoleAuditEvent, RoleParam> builder) {
        // 已申请入帮->坐卿同意
        builder.externalTransitions().fromAmong(RoleAuditState.APPLY, RoleAuditState.ZQ_REJ)
                .to(RoleAuditState.ZQ_PASS)
                .on(RoleAuditEvent.ZQ_PASS)
                .when(machineConditionService.condition())
                .perform(machineActionService.action());
        // 坐卿同意->帮主同意
        builder.externalTransitions().fromAmong(RoleAuditState.ZQ_PASS, RoleAuditState.BZ_REJ)
                .to(RoleAuditState.BZ_PASS)
                .on(RoleAuditEvent.BZ_PASS)
                .when(machineConditionService.condition())
                .perform(machineActionService.action());
        // 已申请入帮->入帮完成
        // 坐卿同意->入帮完成
        // 帮主同意->入帮完成
        builder.externalTransitions().fromAmong(RoleAuditState.APPLY, RoleAuditState.ZQ_PASS, RoleAuditState.BZ_PASS)
                .to(RoleAuditState.DONE)
                .on(RoleAuditEvent.DONE_PASS)
                .when(machineConditionService.condition())
                .perform(machineActionService.action());

        // 已申请入帮->坐卿不同意
        builder.externalTransition().from(RoleAuditState.APPLY).to(RoleAuditState.ZQ_REJ)
                .on(RoleAuditEvent.ZQ_REJECT)
                .when(machineConditionService.condition())
                .perform(machineActionService.action());
        // 坐卿同意->帮主不同意
        builder.externalTransition().from(RoleAuditState.ZQ_PASS).to(RoleAuditState.BZ_REJ)
                .on(RoleAuditEvent.BZ_REJECT)
                .when(machineConditionService.condition())
                .perform(machineActionService.action());
        // 已申请入帮->一言堂不同意
        // 坐卿同意->一言堂不同意
        // 帮主同意->一言堂不同意
        builder.externalTransitions().fromAmong(RoleAuditState.APPLY, RoleAuditState.ZQ_PASS, RoleAuditState.BZ_PASS)
                .to(RoleAuditState.DONE_REJ)
                .on(RoleAuditEvent.DONE_REJECT)
                .when(machineConditionService.condition())
                .perform(machineActionService.action());
        builder.setFailCallback((roleAuditState, roleAuditEvent, roleParam) -> {
            log.warn("不支持动作[{}]修改[{}]状态!", roleAuditEvent.getDesc(), roleAuditState.getDesc());
        });
    }

}
