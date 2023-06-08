package com.wty.flowengine.sdk.exec.behavior;

import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.common.command.Context;
import com.wty.flowengine.sdk.exec.util.CommandContextUtil;
import com.wty.flowengine.sdk.exec.vo.Token;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlowNodeActivityBehavior implements ActivityBehavior {

    @Override
    public void execute(Token token) {
        log.debug("execute behavior on activity {}", token.getCurrentFlowElement().getId());
        leave(token);
    }

    protected void leave(Token token) {
        performDefaultOutgoingBehavior(token);
    }

    private void performDefaultOutgoingBehavior(Token token) {
        CommandContext commandContext = Context.getCommandContext();
        if (commandContext == null) {
            throw new FlowEngineException("commandContext is required");
        }
        CommandContextUtil.getAgenda(commandContext).planTakeOutgoingSequenceFlowsOperation(token);

    }
}
