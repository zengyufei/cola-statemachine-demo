package com.zyf.cola.statemachine.demo.user.machine;

import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public enum UserAuditEvent {

    /**
     * 同意
     */
    PASS("0", "同意"),

    /**
     * 不同意
     */
    REJECT("1", "不同意"),

    /**
     * 已完成
     */
    DONE("2", "最终敲板");

    /**
     * code
     */
    private final String code;

    /**
     * desc
     */
    private final String desc;

    private static final Map<String, UserAuditEvent> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (UserAuditEvent userAuditEvent : EnumSet.allOf(UserAuditEvent.class)) {
            CODE_MAP.put(userAuditEvent.getCode(), userAuditEvent);
        }
    }

    public static UserAuditEvent getEnumsByCode(String code) {
        return CODE_MAP.get(code);
    }

    UserAuditEvent(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    // 省略get/set
}
