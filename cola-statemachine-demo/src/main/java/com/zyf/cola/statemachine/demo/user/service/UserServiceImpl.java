package com.zyf.cola.statemachine.demo.user.service;

import com.zyf.cola.statemachine.demo.user.dao.UserDao;
import com.zyf.cola.statemachine.demo.user.entity.UserDTO;
import com.zyf.cola.statemachine.demo.user.machine.UserAuditEvent;
import com.zyf.cola.statemachine.demo.user.machine.UserAuditState;
import com.zyf.cola.statemachine.demo.base.MachineType;
import com.zyf.cola.statemachine.demo.params.UserParam;
import com.zyf.cola.statemachine.util.StateMachineEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private StateMachineEngine stateMachineEngine;

    @Override
    public boolean audit(UserParam userParam) {
        final String userId = userParam.getUserId();
        final String userName = userParam.getUserName();
        final UserAuditEvent event = userParam.getUserAuditEvent();

        UserDTO dbUser = userDao.selectById(userId);
        // 获取当前状态和事件
        UserAuditState nowState = UserAuditState.getEnumsByCode(dbUser.getAuditState());
        // 执行状态机
        final UserAuditState newState = stateMachineEngine.fire(MachineType.USER_MACHINE, nowState, event, userParam);
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
        return stateMachineEngine.generateUml(MachineType.USER_MACHINE);
    }
}
