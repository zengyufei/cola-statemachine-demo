package com.zyf.cola.statemachine.demo.role.machine;

import com.alibaba.cola.statemachine.Action;
import com.zyf.cola.statemachine.demo.role.dao.RoleDao;
import com.zyf.cola.statemachine.demo.role.params.RoleParam;
import com.zyf.cola.statemachine.service.MachineActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RoleAuditActionServiceImpl implements MachineActionService<RoleAuditState, RoleAuditEvent, RoleParam> {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Action<RoleAuditState, RoleAuditEvent, RoleParam> action() {
        return (from, to, event, context) -> {
            // 打开异常测试
//            final RoleAuditState roleAuditState = RoleAuditState.getEnumsByCode(to.getCode());
//            if (roleAuditState == RoleAuditState.BZ_PASS) {
//                int i = 1 / 0;
//            }
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
            if (RoleAuditEvent.DONE_PASS.equals(event)) {
                sb.append(" 最终完成全部审核!");
            }
            log.info(sb.toString());
            roleDao.updateAuditStatus(to.getCode(), id);
        };
    }
}
