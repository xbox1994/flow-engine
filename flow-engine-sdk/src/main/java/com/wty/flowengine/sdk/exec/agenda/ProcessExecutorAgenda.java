package com.wty.flowengine.sdk.exec.agenda;

import com.wty.flowengine.engine.common.AbstractAgenda;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.sdk.exec.vo.Token;

public class ProcessExecutorAgenda extends AbstractAgenda {

    public ProcessExecutorAgenda(CommandContext commandContext) {
        super(commandContext);
    }

    public void planContinueProcessOperation(Token token) {
        operations.add(new ContinueProcessOperation(token, commandContext));
    }

    public void planTakeOutgoingSequenceFlowsOperation(Token token) {
        operations.add(new TakeOutgoingSequenceFlowsOperation(token, commandContext));
    }

    public void planEndProcessOperation(Token token) {

    }

}
