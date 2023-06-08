package com.wty.flowengine.engine.runtime.behavior;

import com.wty.flow.model.Decision;
import com.wty.flow.model.SequenceFlow;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.runtime.util.ConditionUtil;

public class DecisionActivityBehavior extends FlowNodeActivityBehavior {

    @Override
    protected void leave(Execution execution) {

        Decision decision = (Decision) execution.getCurrentFlowElement();

        SequenceFlow outgoingFlow = null;
        for (SequenceFlow flow : decision.getOutgoingFlows()) {
            if(flow.getId().equals(decision.getDefaultFlowId())) {
                continue;
            }
            if(ConditionUtil.hasTrueCondition(flow, execution)) {
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
        execution.setCurrentFlowElement(outgoingFlow);
        super.leave(execution);
    }
}
