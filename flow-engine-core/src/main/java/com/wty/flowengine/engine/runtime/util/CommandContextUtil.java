package com.wty.flowengine.engine.runtime.util;

import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.common.command.Context;
import com.wty.flowengine.engine.runtime.agenda.FlowEngineAgenda;

public class CommandContextUtil {

    public static ProcessEngineConfiguration getProcessEngineConfiguration() {
        return getProcessEngineConfiguration(getCommandContext());
    }

    public static ProcessEngineConfiguration getProcessEngineConfiguration(CommandContext commandContext) {
        return (ProcessEngineConfiguration) (commandContext.getEngineConfiguration());
    }

    public static FlowEngineAgenda getAgenda(CommandContext commandContext) {
        return commandContext.getSession(FlowEngineAgenda.class);
    }

    public static FlowEngineAgenda getAgenda() {
        return getCommandContext().getSession(FlowEngineAgenda.class);
    }

    public static CommandContext getCommandContext() {
        return Context.getCommandContext();
    }

}
