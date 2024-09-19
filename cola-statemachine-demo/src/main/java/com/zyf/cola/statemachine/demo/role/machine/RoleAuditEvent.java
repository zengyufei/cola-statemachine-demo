package com.zyf.cola.statemachine.demo.role.machine;

import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public enum RoleAuditEvent {

    /**
     * 同意
     */
    ZQ_PASS("0", "坐卿 同意"),
    BZ_PASS("1", "帮主 同意"),
    DONE_PASS("2", "一言堂 同意"),

    /**
     * 不同意
     */
    ZQ_REJECT("3", "坐卿 不同意"),
    BZ_REJECT("4", "帮主 不同意"),
    DONE_REJECT("5", "一言堂 不同意"),
;

    /**
     * code
     */
    private final String code;

    /**
     * desc
     */
    private final String desc;

    private static final Map<String, RoleAuditEvent> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (RoleAuditEvent userAuditEvent : EnumSet.allOf(RoleAuditEvent.class)) {
            CODE_MAP.put(userAuditEvent.getCode(), userAuditEvent);
        }
    }

    public static RoleAuditEvent getEnumsByCode(String code) {
        return CODE_MAP.get(code);
    }

    RoleAuditEvent(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    // 省略get/set
}
