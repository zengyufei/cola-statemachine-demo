package com.zyf.cola.statemachine.demo.base;

import com.zyf.cola.statemachine.util.StateMachineEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MachineType implements StateMachineEnum {

    /**
     * 测试状态机
     */
    TEST_MACHINE("testMachine", "测试状态机");

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
