package com.zyf.cola.statemachine.demo.audit;

import com.zyf.cola.statemachine.demo.audit.machine.AuditState;
import com.zyf.cola.statemachine.demo.pojo.AuditDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuditDaoImpl implements AuditDao {

    static Map<Long, AuditDTO> map = new HashMap<>();

    static {
        {
            final AuditDTO value = new AuditDTO();
            value.setCode("one");
            value.setAuditState(AuditState.APPLY.getCode());
            map.put(1L, value);
        }
        {
            final AuditDTO value = new AuditDTO();
            value.setCode("two");
            value.setAuditState(AuditState.DAD_PASS.getCode());
            map.put(2L, value);
        }
    }

    @Override
    public void updateAuditStatus(String code, Long id) {
        final AuditDTO dto = map.get(id);
        if (dto != null) {
            dto.setCode(code);
        }
    }

    @Override
    public AuditDTO selectById(Long id) {
        return map.get(id);
    }
}
