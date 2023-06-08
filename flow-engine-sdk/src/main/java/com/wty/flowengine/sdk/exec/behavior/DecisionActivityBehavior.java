package com.wty.flowengine.sdk.exec.behavior;

import com.wty.flow.model.Decision;
import com.wty.flow.model.SequenceFlow;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.sdk.exec.util.ConditionUtil;
import com.wty.flowengine.sdk.exec.vo.Token;

public class DecisionActivityBehavior extends FlowNodeActivityBehavior{

    @Override
    protected void leave(Token token) {

        Decision decision = (Decision) token.getCurrentFlowElement();

        SequenceFlow outgoingFlow = null;
        for (SequenceFlow flow : decision.getOutgoingFlows()) {
            if(flow.getId().equals(decision.getDefaultFlowId())) {
                continue;
            }
            if(ConditionUtil.hasTrueCondition(flow, token)) {
                outgoingFlow = flow;
                break;
            }
        }
        if(outgoingFlow==null) {
            outgoingFlow = decision.getDefaultFlow();
        }
        if(outgoingFlow == null) {
            throw new FlowEngineException("No outgoing sequence flow of the decision '" + decision.getId() + "' is available.");
        }
        token.setCurrentFlowElement(outgoingFlow);
        super.leave(token);
    }
}
