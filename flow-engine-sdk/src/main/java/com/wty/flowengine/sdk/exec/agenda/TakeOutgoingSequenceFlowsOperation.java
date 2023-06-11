package com.wty.flowengine.sdk.exec.agenda;

import com.wty.flowengine.rest.demo.test.model.End;
import com.wty.flowengine.rest.demo.test.model.FlowElement;
import com.wty.flowengine.rest.demo.test.model.FlowNode;
import com.wty.flowengine.rest.demo.test.model.SequenceFlow;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.sdk.exec.util.ConditionUtil;
import com.wty.flowengine.sdk.exec.vo.Token;

import java.util.ArrayList;
import java.util.List;

public class TakeOutgoingSequenceFlowsOperation extends AbstractOperation {

    public TakeOutgoingSequenceFlowsOperation(Token token, CommandContext commandContext) {
        super(token, commandContext);
    }

    @Override
    public void run() {
        FlowElement currentFlowElement = token.getCurrentFlowElement();
        if (currentFlowElement instanceof SequenceFlow) {
            handleSequenceFlow((SequenceFlow) currentFlowElement);
        }else if(currentFlowElement instanceof FlowNode){
            handleFlowNode((FlowNode) currentFlowElement);
        }else{
            throw new FlowEngineException("Unknown flow element type: " + currentFlowElement);
        }
    }

    private void handleFlowNode(FlowNode flowNode) {
        if (flowNode instanceof End) {
            agenda.planEndProcessOperation(token);
            return;
        }

        List<SequenceFlow> outgoingSequenceFlows = new ArrayList<>();
        if (flowNode.getOutgoingFlows() != null) {
            for (SequenceFlow outgoingFlow : flowNode.getOutgoingFlows()) {
                if (ConditionUtil.hasTrueCondition(outgoingFlow, token)) {
                    outgoingSequenceFlows.add(outgoingFlow);
                }
            }
        }
        if (outgoingSequenceFlows.size() == 0) {
            throw new FlowEngineException("no outgoing sequenceFlow is available, flowNode id: " + flowNode.getId());
        }

        if (outgoingSequenceFlows.size() == 1) {
            token.setCurrentFlowElement(outgoingSequenceFlows.get(0));
            agenda.planContinueProcessOperation(token);
            return;
        }

        // 产生了分支
        for (SequenceFlow outgoingSequenceFlow : outgoingSequenceFlows) {
            Token childToken = token.createChildToken(outgoingSequenceFlow);
            agenda.planContinueProcessOperation(childToken);
        }
    }

    private void handleSequenceFlow(SequenceFlow sequenceFlow) {
        // record activity ended
        agenda.planContinueProcessOperation(token);
    }
}
