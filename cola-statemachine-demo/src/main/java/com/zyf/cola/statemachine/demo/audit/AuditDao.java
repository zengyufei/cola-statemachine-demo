package com.zyf.cola.statemachine.demo.audit;

import com.zyf.cola.statemachine.demo.pojo.AuditDTO;

public interface AuditDao {
    void updateAuditStatus(String code, Long id);

    AuditDTO selectById(Long id);
}
