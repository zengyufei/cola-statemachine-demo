package com.zyf.cola.statemachine.demo.base;

import com.zyf.cola.statemachine.util.StateMachineEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MachineType implements StateMachineEnum {

    /**
     * 测试 user 状态机
     */
    USER_MACHINE("userMachine", "测试 user 状态机"),

    /**
     * 测试 role 状态机
     */
    ROLE_MACHINE("roleMachine", "测试 role 状态机"),

    ;

    /**
     * code
     */
    private final String code;

    /**
     * desc
     */
    private final String desc;

    @Override
    public String getCode() {
        return code;
    }

}
