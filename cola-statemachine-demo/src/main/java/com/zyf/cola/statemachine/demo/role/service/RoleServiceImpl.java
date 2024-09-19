package com.zyf.cola.statemachine.demo.role.service;

import com.zyf.cola.statemachine.demo.base.MachineType;
import com.zyf.cola.statemachine.demo.role.dao.RoleDao;
import com.zyf.cola.statemachine.demo.role.entity.RoleDTO;
import com.zyf.cola.statemachine.demo.role.machine.RoleAuditEvent;
import com.zyf.cola.statemachine.demo.role.machine.RoleAuditState;
import com.zyf.cola.statemachine.demo.role.params.RoleParam;
import com.zyf.cola.statemachine.util.StateMachineEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private StateMachineEngine stateMachineEngine;

    @Override
    public boolean audit(RoleParam roleParam) {
        final String roleId = roleParam.getId();
        final String roleName = roleParam.getName();
        final RoleAuditEvent event = RoleAuditEvent.getEnumsByCode(roleParam.getAuditEvent());

        RoleDTO dbRole = roleDao.selectById(roleId);
        // 获取当前状态和事件
        RoleAuditState nowState = RoleAuditState.getEnumsByCode(dbRole.getAuditState());
        // 执行状态机
        final RoleAuditState newState = stateMachineEngine.fire(MachineType.ROLE_MACHINE, nowState, event, roleParam);
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
        return stateMachineEngine.generateUml(MachineType.ROLE_MACHINE);
    }
}
