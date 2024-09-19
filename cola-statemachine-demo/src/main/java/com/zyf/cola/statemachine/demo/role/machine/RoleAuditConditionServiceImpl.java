package com.zyf.cola.statemachine.demo.role.machine;

import cn.hutool.core.lang.Console;
import com.alibaba.cola.statemachine.Condition;
import com.zyf.cola.statemachine.demo.role.params.RoleParam;
import com.zyf.cola.statemachine.service.MachineConditionService;
import org.springframework.stereotype.Component;

@Component
public class RoleAuditConditionServiceImpl implements MachineConditionService<RoleParam> {

    @Override
    public Condition<RoleParam> condition() {
        return context -> {
            final String roleId = context.getId();
            final String event = context.getEvent();
            final String roleName = context.getName();
            final RoleAuditEvent nowEvent = RoleAuditEvent.getEnumsByCode(event);


            Console.log("正常审核 角色: {}, event: {}", roleName, nowEvent.getDesc());

            return true;
        };
    }

}
