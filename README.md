# 用法

---

## 1. 添加依赖

```xml
<dependency>
    <groupId>com.zyf.cola.statemachine.util</groupId>
    <artifactId>cola-statemachine-util</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 2. 定义业务
```java
@Getter
@AllArgsConstructor
public enum MachineEnum implements StateMachineEnum {

    /**
     * 测试状态机
     */
    TEST_MACHINE("testMachine", "测试状态机");

    /**
     * code
     */
    private String code;

    /**
     * desc
     */
    private String desc;

    @Override
    public String getCode() {
        return code;
    }

}
```

## 3. 定义 AuditState, AuditEvent, AuditContext, ConditionService, ActionService

具体不描述，自己看代码

## 4. 定义该业务状态机
```java
@Component
public class AuditMachine extends StateMachineStrategy<AuditState, AuditEvent, AuditContext> {

    @Autowired
    private ConditionService conditionService;

    @Autowired
    private ActionService actionService;

    @Override
    public String getMachineType() {
        return MachineEnum.TEST_MACHINE.getCode();
    }

    /**
     * | From(开始状态) | To(抵达状态) | Event(事件) | When(条件)            | Perform(执行动作)  |
     * | -------------- | ------------ | ----------- | --------------------- | ------------------ |
     * | 已申请      | 爸爸同意 | 审核通过    | passOrRejectCondition | passOrRejectAction |
     * | 爸爸同意    | 妈妈同意 | 审核通过    | passOrRejectCondition | passOrRejectAction |
     * | 已申请     | 爸爸不同意 | 审核驳回    | passOrRejectCondition | passOrRejectAction |
     * | 爸爸同意   | 妈妈不同意 | 审核驳回    | passOrRejectCondition | passOrRejectAction |
     * | 已申请    | 已完成状态    | 已完成        | doneCondition        | doneAction        |
     * | 爸爸同意  | 已完成状态    | 已完成        | doneCondition        | doneAction        |
     * | 妈妈同意  | 已完成状态    | 已完成        | doneCondition        | doneAction        |
     */
    @Override
    public void build(StateMachineBuilder<AuditState, AuditEvent, AuditContext> builder) {
        // 已申请->爸爸同意
        builder.externalTransition().from(AuditState.APPLY).to(AuditState.DAD_PASS)
                .on(AuditEvent.PASS)
                .when(conditionService.passOrRejectCondition())
                .perform(actionService.passOrRejectAction());
        // 已申请->爸爸不同意
        builder.externalTransition().from(AuditState.APPLY).to(AuditState.DAD_REJ)
                .on(AuditEvent.REJECT)
                .when(conditionService.passOrRejectCondition())
                .perform(actionService.passOrRejectAction());
        // 爸爸同意->妈妈同意
        builder.externalTransition().from(AuditState.DAD_PASS).to(AuditState.MOM_PASS)
                .on(AuditEvent.PASS)
                .when(conditionService.passOrRejectCondition())
                .perform(actionService.passOrRejectAction());
        // 爸爸同意->妈妈不同意
        builder.externalTransition().from(AuditState.DAD_PASS).to(AuditState.MOM_REJ)
                .on(AuditEvent.REJECT)
                .when(conditionService.passOrRejectCondition())
                .perform(actionService.passOrRejectAction());
        // 已申请->已完成
        // 爸爸同意->已完成
        // 妈妈同意->已完成
        builder.externalTransitions().fromAmong(AuditState.APPLY, AuditState.DAD_PASS, AuditState.MOM_PASS)
                .to(AuditState.DONE)
                .on(AuditEvent.DONE)
                .when(conditionService.doneCondition())
                .perform(actionService.doneAction());
    }

}
```


## 5. 定义业务方法
```java
@Service
@Slf4j
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditDao auditDao;

    @Autowired
    private StateMachineEngine stateMachineEngine;

    @Override
    public void audit(AuditContext auditContext) {
        Long id = auditContext.getId();
        AuditDTO old = auditDao.selectById(id);
        String oldState = old.getAuditState();
        Integer auditEvent = auditContext.getAuditEvent();
        // 获取当前状态和事件
        AuditState nowState = AuditState.getEnumsByCode(oldState);
        AuditEvent nowEvent = AuditEvent.getEnumsByCode(auditEvent);
        // 执行状态机
        stateMachineEngine.fire(MachineEnum.TEST_MACHINE, nowState, nowEvent, auditContext);
    }

    @Override
    public String uml() {
        return stateMachineEngine.generateUml(MachineEnum.TEST_MACHINE);
    }
}
```

## 6. 测试
```java
@SpringBootTest
class ColaStatemachineDemoApplicationTests {

    @Autowired
    private AuditService auditService;

    @Test
    public void babaPass() {
        AuditContext auditContext = new AuditContext();
        auditContext.setId(1L);
        auditContext.setAuditEvent(0);

        auditService.audit(auditContext);
    }
  // 打印的日志： 2024-02-23T11:55:02.019+08:00  INFO 11520 --- [           main] c.z.c.s.demo.audit.AuditActionServiceImpl     : passOrRejectAction from APPLY, to DAD_PASS, on event PASS, id:1
}
```
