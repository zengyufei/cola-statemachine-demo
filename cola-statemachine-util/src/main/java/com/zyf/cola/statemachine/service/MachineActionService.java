package com.zyf.cola.statemachine.service;

import com.alibaba.cola.statemachine.Action;
import com.zyf.cola.statemachine.context.MachineContext;

public interface MachineActionService<S, E, C extends MachineContext> {

    Action<S, E, C> action();
}
