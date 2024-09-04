package com.zyf.cola.statemachine.demo.user.machine;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.statemachine.Condition;
import com.zyf.cola.statemachine.demo.params.UserParam;
import com.zyf.cola.statemachine.service.MachineConditionService;
import org.springframework.stereotype.Component;

@Component
public class UserAuditConditionServiceImpl implements MachineConditionService<UserParam> {

    @Override
    public Condition<UserParam> condition() {
        return context -> {
            final String userId = context.getUserId();
            final String event = context.getEvent();
            final String userName = context.getUserName();
            final UserAuditEvent nowEvent = UserAuditEvent.getEnumsByCode(event);

            if (StrUtil.equals(userName, "爸爸") && UserAuditEvent.DONE.equals(nowEvent)) {
                Console.log("爸爸想偷偷敲板, 但是没权利, 被捉现行");
                return false;
            } else if (StrUtil.equals(userName, "妈妈") && UserAuditEvent.DONE.equals(nowEvent)) {
                Console.log("妈妈敲板通过了");
            } else {
                Console.log("正常审核 userId: {}, event: {}", userId, nowEvent.getDesc());
            }

            return true;
        };
    }

}
