package com.zyf.cola.statemachine.demo.user.machine;

import com.alibaba.cola.statemachine.Action;
import com.zyf.cola.statemachine.demo.user.UserDao;
import com.zyf.cola.statemachine.demo.params.UserParam;
import com.zyf.cola.statemachine.service.MachineActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserAuditActionServiceImpl implements MachineActionService<UserAuditState, UserAuditEvent, UserParam> {

    @Autowired
    private UserDao userDao;

    @Override
    public Action<UserAuditState, UserAuditEvent, UserParam> action() {
        return (from, to, event, context) -> {
            StringBuilder sb = new StringBuilder();
            final String id = context.getId();
            sb.append("id: [").append(id).append("] ");
            final String eventDesc = event.getDesc();
            sb.append("因 [").append(eventDesc).append("] 动作, ");
            final String fromDesc = from.getDesc();
            sb.append("状态变化 [").append(fromDesc).append("]");
            sb.append(" -> ");
            final String toDesc = to.getDesc();
            sb.append("[").append(toDesc).append("]");
            if (UserAuditEvent.DONE.equals(event)) {
                sb.append(" 最终完成全部审核!");
            }
            log.info(sb.toString());
            userDao.updateAuditStatus(to.getCode(), id);
        };
    }
}
