package com.wty.flowengine.sdk.exec.agenda;

import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.sdk.exec.util.CommandContextUtil;
import com.wty.flowengine.sdk.exec.vo.Token;

public abstract class AbstractOperation implements Runnable {
    protected final Token token;
    protected final CommandContext commandContext;
    protected final ProcessExecutorAgenda agenda;

    public AbstractOperation(Token token, CommandContext commandContext) {
        this.token = token;
        this.commandContext = commandContext;
        this.agenda = CommandContextUtil.getAgenda(commandContext);
    }

    public CommandContext getCommandContext() {
        return commandContext;
    }
}
