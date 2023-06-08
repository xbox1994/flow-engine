package com.wty.flowengine.sdk.exec.agenda;

import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.sdk.exec.vo.Token;

public class EndProcessOperation extends AbstractOperation {
    public EndProcessOperation(Token token, CommandContext commandContext) {
        super(token, commandContext);
    }

    @Override
    public void run() {
        System.out.println("流程结束，结束节点：" + token.getCurrentFlowElement());
    }
}
