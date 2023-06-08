package com.wty.flowengine.engine.common.command;

import com.wty.flowengine.engine.common.AbstractEngineConfiguration;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandContextInterceptor extends AbstractCommandInterceptor {

    private AbstractEngineConfiguration engineConfiguration;

    private CommandContextFactory commandContextFactory;

    public CommandContextInterceptor() {

    }

    @Override
    public <T> T execute(Command<T> command, CommandExecutor commandExecutor) {
        // 初始化 CommandContext
        CommandContext commandContext = Context.getCommandContext();
        if (commandContext == null) {
            commandContext = commandContextFactory.create(command);
        }

        try {
            Context.setCommandContext(commandContext);
            return next.execute(command, commandExecutor);
        } catch (Exception e) {
            commandContext.handleException(e);
        } finally {
            Context.removeCommandContext();
        }

        // rethrow exception
        if (commandContext.getException() != null) {
            Exception exception = commandContext.getException();
            if (exception instanceof FlowEngineException) {
                throw (FlowEngineException) exception;
            } else {
                throw new FlowEngineException("Exception during command execution.", exception);
            }
        }
        return null;
    }

    public AbstractEngineConfiguration getEngineConfiguration() {
        return engineConfiguration;
    }

    public void setEngineConfiguration(AbstractEngineConfiguration engineConfiguration) {
        this.engineConfiguration = engineConfiguration;
    }

    public void setCommandContextFactory(CommandContextFactory commandContextFactory) {
        this.commandContextFactory = commandContextFactory;
    }
}
