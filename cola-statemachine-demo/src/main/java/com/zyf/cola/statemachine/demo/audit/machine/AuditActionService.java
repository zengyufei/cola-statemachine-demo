package com.zyf.cola.statemachine.demo.audit.machine;

import com.alibaba.cola.statemachine.Action;

public interface AuditActionService {

    /**
     * 通用审核通过/驳回执行动作
     * 覆盖审核正向流程，以及驳回流程
     * 已申请->爸爸同意->妈妈同意
     * 已申请->爸爸不同意
     * 爸爸同意->妈妈不同意
     *
     * @return Action
     */
    Action<AuditState, AuditEvent, AuditContext> passOrRejectAction();

    /**
     * 已完成执行动作
     *
     * @return Action
     */
    Action<AuditState, AuditEvent, AuditContext> doneAction();
}
