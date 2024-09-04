package com.zyf.cola.statemachine.demo.user;

import com.zyf.cola.statemachine.demo.user.machine.UserAuditState;
import com.zyf.cola.statemachine.demo.entity.UserDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    static Map<String, UserDTO> map = new HashMap<>();

    static {
        {
            final UserDTO value = new UserDTO();
            value.setCode("one");
            value.setAuditState(UserAuditState.APPLY.getCode());
            map.put("1", value);
        }
        {
            final UserDTO value = new UserDTO();
            value.setCode("two");
            value.setAuditState(UserAuditState.DAD_PASS.getCode());
            map.put("2", value);
        }
        {
            final UserDTO value = new UserDTO();
            value.setCode("three");
            value.setAuditState(UserAuditState.MOM_PASS.getCode());
            map.put("3", value);
        }
    }

    @Override
    public void updateAuditStatus(String code, String id) {
        final UserDTO dto = map.get(id);
        if (dto != null) {
            dto.setAuditState(code);
        }
    }

    @Override
    public UserDTO selectById(String id) {
        return map.get(id);
    }
}
