package com.wty.flowengine.engine.runtime.agenda;

import com.wty.flowengine.engine.common.AbstractAgenda;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.runtime.agenda.operation.ContinueProcessOperation;
import com.wty.flowengine.engine.runtime.agenda.operation.EndExecutionOperation;
import com.wty.flowengine.engine.runtime.agenda.operation.TakeOutgoingSequenceFlowsOperation;

public class FlowEngineAgenda extends AbstractAgenda {

    public FlowEngineAgenda(CommandContext commandContext) {
        super(commandContext);
    }

    public void planContinueProcessOperation(Execution execution) {
        operations.add(new ContinueProcessOperation(commandContext, execution));
    }

    public void planTakeOutgoingSequenceFlowsOperation(Execution execution, boolean evaluateCondition) {
        operations.add(new TakeOutgoingSequenceFlowsOperation(commandContext, execution, evaluateCondition));
    }

    public void planEndProcessOperation(Execution execution) {
        operations.add(new EndExecutionOperation(commandContext, execution));
    }

    public void planContinueExecutionOperation(Execution execution, boolean checkManual) {
        operations.add(new ContinueProcessOperation(commandContext, execution, checkManual));
    }
}



