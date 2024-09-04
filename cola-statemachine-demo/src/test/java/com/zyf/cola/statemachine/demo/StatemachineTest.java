package com.zyf.cola.statemachine.demo;

import com.zyf.cola.statemachine.demo.user.UserService;
import com.zyf.cola.statemachine.demo.user.machine.UserAuditEvent;
import com.zyf.cola.statemachine.demo.params.UserParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StatemachineTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUml() {
        final String uml = userService.uml();
        System.out.println(uml);
    }

    @Test
    public void babaPass() {
        UserParam userParam = new UserParam();
        userParam.setUserId("1");
        userParam.setUserName("爸爸");
        userParam.setUserAuditEvent(UserAuditEvent.PASS);

        userService.audit(userParam);
    }

    @Test
    public void mamaPass() {
        UserParam userParam = new UserParam();
        userParam.setUserId("2");
        userParam.setUserName("妈妈");
        userParam.setUserAuditEvent(UserAuditEvent.PASS);

        userService.audit(userParam);
    }


    @Test
    public void donePass() {
        UserParam userParam = new UserParam();
        userParam.setUserId("3");
        userParam.setUserName("妈妈");
        userParam.setUserAuditEvent(UserAuditEvent.DONE);

        userService.audit(userParam);
    }



    @Test
    public void errorDonePass() {
        UserParam userParam = new UserParam();
        userParam.setUserId("3");
        userParam.setUserName("爸爸");
        userParam.setUserAuditEvent(UserAuditEvent.DONE);

        userService.audit(userParam);
    }

}
