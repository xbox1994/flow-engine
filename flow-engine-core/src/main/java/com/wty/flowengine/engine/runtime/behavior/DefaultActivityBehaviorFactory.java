package com.wty.flowengine.engine.runtime.behavior;

import com.wty.flowengine.rest.demo.test.model.FlowElement;
import com.wty.flowengine.rest.demo.test.model.ServiceTask;
import com.wty.flowengine.converter.constants.FlowElementType;
import com.wty.flowengine.converter.util.FlowElementUtil;
import com.wty.flowengine.engine.domain.Execution;

public class DefaultActivityBehaviorFactory implements ActivityBehaviorFactory {

    private final ServiceTaskBehaviorFactory serviceTaskBehaviorFactory;

    public DefaultActivityBehaviorFactory(ServiceTaskBehaviorFactory serviceTaskBehaviorFactory) {
        this.serviceTaskBehaviorFactory = serviceTaskBehaviorFactory;
    }

    @Override
    public ActivityBehavior create(FlowElement flowElement) {
        FlowElementType flowElementType = FlowElementUtil.get(flowElement);
        switch (flowElementType) {
            case START:
                return new StartActivityBehavior();
            case SERVICE_TASK:
                return serviceTaskBehaviorFactory.create((ServiceTask) flowElement);
            case END:
                return createEndActivityBehavior();
            case DECISION:
                return new DecisionActivityBehavior();
            case USER_TASK:
                return new UserTaskActivityBehavior();
            case CALL_ACTIVITY:
                return new CallActivityBehavior();
            default:
                throw new RuntimeException("Not implemented");
        }
    }

    protected ActivityBehavior createEndActivityBehavior() {
        return new FlowNodeActivityBehavior() {
            @Override
            public void execute(Execution execution) {
                System.out.println("执行「结束」节点：" + execution.getCurrentFlowElement());
                super.leave(execution);
            }
        };
    }
}
