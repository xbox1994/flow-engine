package com.wty.flowengine.sdk.exec.agenda;

import com.wty.flowengine.rest.demo.test.model.FlowElement;
import com.wty.flowengine.rest.demo.test.model.FlowNode;
import com.wty.flowengine.rest.demo.test.model.SequenceFlow;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.sdk.exec.behavior.ActivityBehavior;
import com.wty.flowengine.sdk.exec.vo.Token;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContinueProcessOperation extends AbstractOperation {


    public ContinueProcessOperation(Token token, CommandContext commandContext) {
        super(token, commandContext);
    }

    @Override
    public void run() {
        FlowElement currentFlowElement = token.getCurrentFlowElement();
        if (currentFlowElement instanceof FlowNode) {
            continueThroughFlowNode((FlowNode) currentFlowElement);
        } else if (currentFlowElement instanceof SequenceFlow) {
            continueThroughSequenceFlow((SequenceFlow) currentFlowElement);
        } else {
            throw new FlowEngineException("invalid flowElementType: " + currentFlowElement);
        }
    }

    private void continueThroughSequenceFlow(SequenceFlow sequenceFlow) {
        FlowElement target = sequenceFlow.getTarget();
        if (target == null) {
            throw new FlowEngineException("source is null of sequence " + sequenceFlow.getId());
        }
        token.setCurrentFlowElement(target);
        log.debug("Sequence flow '{}' encountered. Continuing process by following it using token {}", sequenceFlow.getId(), token);
        agenda.planContinueProcessOperation(token);
    }


    private void continueThroughFlowNode(FlowNode flowNode) {
        if (flowNode.getBehavior() == null) {
            throw new FlowEngineException("No behavior set for '{}'" + flowNode.getId());
        }
        ActivityBehavior behavior = (ActivityBehavior) flowNode.getBehavior();
        log.debug("Executing behavior {} on activity '{}' with token {}", behavior, flowNode, token);
        behavior.execute(token);
    }

}
