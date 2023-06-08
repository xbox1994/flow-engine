package com.wty.flowengine.sdk.exec.interceptor;

import com.wty.flowengine.engine.common.agenda.Agenda;
import com.wty.flowengine.engine.common.command.AbstractCommandInterceptor;
import com.wty.flowengine.engine.common.command.Command;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.common.command.CommandExecutor;
import com.wty.flowengine.sdk.exec.util.CommandContextUtil;

public class CommandInvoker extends AbstractCommandInterceptor {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T execute(Command<T> command, CommandExecutor commandExecutor) {
        CommandContext commandContext = CommandContextUtil.getCommandContext();
        CommandContextUtil.getAgenda().planOperation(() -> commandContext.setResult(command.execute(commandContext)));
        executeOperations();
        return (T) commandContext.getResult();
    }

    protected void executeOperations() {
        Agenda agenda = CommandContextUtil.getAgenda();
        while (!agenda.isEmpty()) {
            Runnable operation = agenda.getNextOperation();
            operation.run();
        }
    }
}
