package com.wty.flowengine.sdk.exec.behavior;

import com.wty.flowengine.rest.demo.test.model.FlowElement;
import com.wty.flowengine.rest.demo.test.model.ServiceTask;
import com.wty.flowengine.converter.constants.FlowElementType;
import com.wty.flowengine.converter.util.FlowElementUtil;
import com.wty.flowengine.sdk.exec.vo.Token;

public class DefaultActivityBehaviorFactory implements ActivityBehaviorFactory {

    @Override
    public ActivityBehavior create(FlowElement flowElement) {
        FlowElementType flowElementType = FlowElementUtil.get(flowElement);
        switch (flowElementType) {
            case START:
                return new StartActivityBehavior();
            case SERVICE_TASK:
                return ServiceTaskBehaviorFactory.create((ServiceTask) flowElement);
            case END:
                return createEndActivityBehavior();
            case DECISION:
                return new DecisionActivityBehavior();
            default:
                throw new RuntimeException("Not implemented");
        }
    }

    protected ActivityBehavior createEndActivityBehavior() {
        return new FlowNodeActivityBehavior() {
            @Override
            public void execute(Token token) {
                System.out.println("执行「结束」节点：" + token.getCurrentFlowElement());
                super.leave(token);
            }
        };
    }
}
