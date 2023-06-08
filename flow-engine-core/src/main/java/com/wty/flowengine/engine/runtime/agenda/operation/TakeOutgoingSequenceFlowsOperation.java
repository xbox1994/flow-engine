package com.wty.flowengine.engine.runtime.agenda.operation;

import com.wty.flow.model.FlowElement;
import com.wty.flow.model.FlowNode;
import com.wty.flow.model.SequenceFlow;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.runtime.util.CommandContextUtil;
import com.wty.flowengine.engine.runtime.util.ConditionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TakeOutgoingSequenceFlowsOperation extends AbstractOperation {

    /**
     * 是否判断转移条件
     */
    private final boolean evaluateCondition;

    public TakeOutgoingSequenceFlowsOperation(CommandContext commandContext, Execution execution, boolean evaluateCondition) {
        super(commandContext, execution);
        this.evaluateCondition = evaluateCondition;
    }

    @Override
    public void run() {
        FlowElement currentFlowElement = execution.getCurrentFlowElement();
        if (currentFlowElement instanceof SequenceFlow) {
            handleSequenceFlow((SequenceFlow) currentFlowElement);
        } else if (currentFlowElement instanceof FlowNode) {
            handleFlowNode((FlowNode) currentFlowElement);
        } else {
            throw new FlowEngineException("Unknown flow element type: " + currentFlowElement);
        }
    }

    private void handleSequenceFlow(SequenceFlow currentFlowElement) {
        // record activity ended
        agenda.planContinueProcessOperation(execution);
    }

    private void handleFlowNode(FlowNode flowNode) {
        CommandContextUtil.getProcessEngineConfiguration()
                .getProcessEventLogRepository().recordActivityEnd(execution, flowNode);

        // select available paths
        List<SequenceFlow> outgoingSequenceFlows = new ArrayList<>();
        if (flowNode.getOutgoingFlows() != null) {
            for (SequenceFlow outgoingFlow : flowNode.getOutgoingFlows()) {
                if (!evaluateCondition || ConditionUtil.hasTrueCondition(outgoingFlow, execution)) {
                    outgoingSequenceFlows.add(outgoingFlow);
                }
            }
        }
        if (outgoingSequenceFlows.size() == 0) {
            log.debug("no outgoing sequenceFlow is available, end process. flowNode id: " + flowNode.getId());
            agenda.planEndProcessOperation(execution);
        }

        if (outgoingSequenceFlows.size() == 1) {
            execution.setCurrentFlowElement(outgoingSequenceFlows.get(0));
            agenda.planContinueProcessOperation(execution);
            return;
        }

        // 产生了分支
        for (SequenceFlow outgoingSequenceFlow : outgoingSequenceFlows) {
            Execution childExecution = execution.createChild(outgoingSequenceFlow);
            agenda.planContinueProcessOperation(childExecution);
        }
    }
}
