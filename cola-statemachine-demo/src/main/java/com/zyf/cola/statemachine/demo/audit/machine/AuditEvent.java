package com.zyf.cola.statemachine.demo.audit.machine;

import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public enum AuditEvent {

    /**
     * 同意
     */
    PASS(0, "同意"),

    /**
     * 不同意
     */
    REJECT(1, "不同意"),

    /**
     * 已完成
     */
    DONE(2, "已完成");

    /**
     * code
     */
    private Integer code;

    /**
     * desc
     */
    private String desc;

    private static final Map<Integer, AuditEvent> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (AuditEvent auditEvent : EnumSet.allOf(AuditEvent.class)) {
            CODE_MAP.put(auditEvent.getCode(), auditEvent);
        }
    }

    public static AuditEvent getEnumsByCode(Integer code) {
        return CODE_MAP.get(code);
    }

    AuditEvent(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    // 省略get/set
}
