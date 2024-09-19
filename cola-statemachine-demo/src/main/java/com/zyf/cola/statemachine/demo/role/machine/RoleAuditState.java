package com.zyf.cola.statemachine.demo.role.machine;

import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public enum RoleAuditState {

    /**
     * 已申请入帮
     */
    APPLY("APPLY", "已申请入帮"),
    /**
     * 坐卿同意
     */
    ZQ_PASS("ZQ_PASS", "坐卿同意"),
    /**
     * 帮主同意
     */
    BZ_PASS("BZ_PASS", "帮主同意"),
    /**
     * 坐卿不同意
     */
    ZQ_REJ("ZQ_REJ", "坐卿不同意"),
    /**
     * 帮主不同意
     */
    BZ_REJ("BZ_REJ", "帮主不同意"),
    /**
     * 一言堂不同意
     */
    DONE_REJ("DONE_REJ", "一言堂不同意"),
    /**
     * 入帮完成
     */
    DONE("DONE", "入帮完成");

    private static final Map<String, RoleAuditState> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (RoleAuditState userAuditState : EnumSet.allOf(RoleAuditState.class)) {
            CODE_MAP.put(userAuditState.getCode(), userAuditState);
        }
    }

    public static RoleAuditState getEnumsByCode(String code) {
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

    RoleAuditState(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    // 省略get/set
}
