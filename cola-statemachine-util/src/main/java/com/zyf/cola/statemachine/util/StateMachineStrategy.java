package com.zyf.cola.statemachine.util;

import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import org.springframework.beans.factory.InitializingBean;

public abstract class StateMachineStrategy<S, E, C> implements InitializingBean {

    public abstract String getMachineType();

    public abstract void build(StateMachineBuilder<S, E, C> builder);

    @Override
    public void afterPropertiesSet() throws Exception {
        StateMachineBuilder<S, E, C> builder = StateMachineBuilderFactory.create();
        build(builder);
        builder.build(getMachineType());
    }
}
