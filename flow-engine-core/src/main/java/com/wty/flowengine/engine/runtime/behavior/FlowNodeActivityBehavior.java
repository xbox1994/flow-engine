package com.wty.flowengine.engine.runtime.behavior;

import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.common.command.Context;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.runtime.util.CommandContextUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlowNodeActivityBehavior implements ActivityBehavior {

    @Override
    public void execute(Execution execution) {
        log.debug("execute behavior on activity {}", execution.getCurrentFlowElement().getId());
        leave(execution);
    }

    protected void leave(Execution execution) {
        performDefaultOutgoingBehavior(execution);
    }

    private void performDefaultOutgoingBehavior(Execution execution) {
        CommandContext commandContext = Context.getCommandContext();
        if (commandContext == null) {
            throw new FlowEngineException("commandContext is required");
        }
        CommandContextUtil.getAgenda(commandContext).planTakeOutgoingSequenceFlowsOperation(execution, true);

    }
}
