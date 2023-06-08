package com.wty.flowengine.engine.common.command;

import com.wty.flowengine.engine.common.AbstractEngineConfiguration;

public class CommandContextFactory {

    private final AbstractEngineConfiguration engineConfiguration;

    public CommandContextFactory(AbstractEngineConfiguration engineConfiguration) {
        this.engineConfiguration = engineConfiguration;
    }

    public <T> CommandContext create(Command<T> command) {
        CommandContext commandContext = new CommandContext(command);
        commandContext.setEngineConfiguration(engineConfiguration);
        commandContext.setSessionFactories(engineConfiguration.getSessionFactories());
        return commandContext;
    }

}
