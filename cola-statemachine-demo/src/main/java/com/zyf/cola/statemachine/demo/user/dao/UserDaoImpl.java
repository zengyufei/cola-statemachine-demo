package com.zyf.cola.statemachine.demo.user.dao;

import com.zyf.cola.statemachine.demo.user.entity.UserDTO;
import com.zyf.cola.statemachine.demo.user.machine.UserAuditState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    static Map<String, UserDTO> map = new HashMap<>();

    static {
        {
            final UserDTO value = new UserDTO();
            value.setId("1");
            value.setIdCard("one");
            value.setAuditState(UserAuditState.APPLY.getCode());
            map.put("1", value);
        }
        {
            final UserDTO value = new UserDTO();
            value.setId("2");
            value.setIdCard("two");
            value.setAuditState(UserAuditState.DAD_PASS.getCode());
            map.put("2", value);
        }
        {
            final UserDTO value = new UserDTO();
            value.setId("3");
            value.setIdCard("three");
            value.setAuditState(UserAuditState.MOM_PASS.getCode());
            map.put("3", value);
        }
    }

    @Override
    public void updateAuditStatus(String userAuditStateEnumCode, String id) {
        final UserDTO dto = map.get(id);
        if (dto != null) {
            dto.setAuditState(userAuditStateEnumCode);
        }
    }

    @Override
    public UserDTO selectById(String id) {
        return map.get(id);
    }
}
