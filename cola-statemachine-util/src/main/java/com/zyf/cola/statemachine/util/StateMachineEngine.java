package com.zyf.cola.statemachine.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.StateMachineFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AutoConfiguration
@ConditionalOnClass(StateMachineEngine.class)
public class StateMachineEngine implements InitializingBean {

    private Map<String, StateMachineStrategy> stateMachineMap;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 根据枚举获取状态机实例key
     *
     * @param stateMachineEnum stateMachineEnum
     * @return String
     */
    private String getMachine(StateMachineEnum stateMachineEnum) {
        return stateMachineMap.get(stateMachineEnum.getCode()).getMachineType();
    }

    /**
     * 根据枚举获取状态机示例，并根据当前状态、事件、上下文，进行状态流转
     *
     * @param stateMachineEnum stateMachineEnum
     * @param auditState auditState
     * @param auditEvent auditEvent
     * @param auditContext auditContext
     * @return AuditState
     */
    public <S, E, C> S fire(StateMachineEnum stateMachineEnum, S auditState,
                           E auditEvent, C auditContext) {
        StateMachine<S, E, C> stateMachine = StateMachineFactory.get(getMachine(stateMachineEnum));
        return stateMachine.fireEvent(auditState, auditEvent, auditContext);
    }

    /**
     * 根据枚举获取状态机示例的状态DSL UML图
     *
     * @param stateMachineEnum stateMachineEnum
     * @return String
     */
    public <S, E, C> String generateUml(StateMachineEnum stateMachineEnum){
        StateMachine<S, E, C> stateMachine = StateMachineFactory.get(getMachine(stateMachineEnum));
        return stateMachine.generatePlantUML();
    }

    /**
     * 获取所有实现了接口的状态机
     */
    @Override
    public void afterPropertiesSet() {
        Map<String, StateMachineStrategy> beansOfType = applicationContext.getBeansOfType(StateMachineStrategy.class);
        stateMachineMap = Optional.of(beansOfType)
                .map(beansOfTypeMap -> beansOfTypeMap.values().stream()
                        .filter(stateMachineHandler -> StrUtil.isNotBlank(stateMachineHandler.getMachineType()))
                        .collect(Collectors.toMap(StateMachineStrategy::getMachineType, Function.identity())))
                .orElse(new HashMap<>(8));
    }
}
