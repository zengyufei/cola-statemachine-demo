package com.zyf.cola.statemachine.demo.audit.machine;

import com.zyf.cola.statemachine.util.StateMachineEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MachineEnum implements StateMachineEnum {

    /**
     * 测试状态机
     */
    TEST_MACHINE("testMachine", "测试状态机");

    /**
     * code
     */
    private String code;

    /**
     * desc
     */
    private String desc;

    @Override
    public String getCode() {
        return code;
    }

}
