package com.zyf.cola.statemachine.demo.user.machine;

import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public enum UserAuditState {

    /**
     * 已申请
     */
    APPLY("APPLY", "已申请"),
    /**
     * 爸爸同意
     */
    DAD_PASS("DAD_PASS", "爸爸同意"),
    /**
     * 妈妈同意
     */
    MOM_PASS("MOM_PASS", "妈妈同意"),
    /**
     * 爸爸不同意
     */
    DAD_REJ("DAD_REJ", "爸爸不同意"),
    /**
     * 妈妈不同意
     */
    MOM_REJ("MOM_REJ", "妈妈不同意"),
    /**
     * 已完成
     */
    DONE("DONE", "已完成");

    private static final Map<String, UserAuditState> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (UserAuditState userAuditState : EnumSet.allOf(UserAuditState.class)) {
            CODE_MAP.put(userAuditState.getCode(), userAuditState);
        }
    }

    public static UserAuditState getEnumsByCode(String code) {
        return CODE_MAP.get(code);
    }

    /**
     * code
     */
    private String code;

    /**
     * desc
     */
    private String desc;

    UserAuditState(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    // 省略get/set
}
