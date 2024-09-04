package com.zyf.cola.statemachine.service;

import com.alibaba.cola.statemachine.Condition;
import com.zyf.cola.statemachine.context.MachineContext;

public interface MachineConditionService<C extends MachineContext > {

    Condition<C> condition();
}
