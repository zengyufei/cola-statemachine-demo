package com.zyf.cola.statemachine.demo.role.dao;

import com.zyf.cola.statemachine.demo.role.entity.RoleDTO;
import com.zyf.cola.statemachine.demo.role.machine.RoleAuditState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RoleDaoImpl implements RoleDao {

    static Map<String, RoleDTO> map = new HashMap<>();

    static {
        {
            final RoleDTO value = new RoleDTO();
            value.setId("1");
            value.setName("帮主");
            value.setAuditState(RoleAuditState.APPLY.getCode());
            map.put("1", value);
        }
        {
            final RoleDTO value = new RoleDTO();
            value.setId("2");
            value.setName("坐卿");
            value.setAuditState(RoleAuditState.ZQ_PASS.getCode());
            map.put("2", value);
        }
        {
            final RoleDTO value = new RoleDTO();
            value.setId("3");
            value.setName("普通帮众");
            value.setAuditState(RoleAuditState.ZQ_REJ.getCode());
            map.put("3", value);
        }
        {
            final RoleDTO value = new RoleDTO();
            value.setId("4");
            value.setName("普通帮众");
            value.setAuditState(RoleAuditState.APPLY.getCode());
            map.put("4", value);
        }
    }

    @Override
    public void updateAuditStatus(String roleAuditStateEnumCode, String id) {
        final RoleDTO dto = map.get(id);
        if (dto != null) {
            dto.setAuditState(roleAuditStateEnumCode);
        }
    }

    @Override
    public RoleDTO selectById(String id) {
        return map.get(id);
    }
}
