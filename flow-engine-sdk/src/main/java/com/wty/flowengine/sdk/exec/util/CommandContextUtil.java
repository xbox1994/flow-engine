package com.wty.flowengine.sdk.exec.util;

import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.common.command.Context;
import com.wty.flowengine.sdk.ProcessExecutorConfiguration;
import com.wty.flowengine.sdk.exec.agenda.ProcessExecutorAgenda;

public class CommandContextUtil {

    public static CommandContext getCommandContext() {
        return Context.getCommandContext();
    }

    public static ProcessExecutorAgenda getAgenda(CommandContext commandContext) {
        return commandContext.getSession(ProcessExecutorAgenda.class);
    }

    public static ProcessExecutorAgenda getAgenda() {
        return getAgenda(getCommandContext());
    }

    public static ProcessExecutorConfiguration getProcessExecutorConfiguration() {
        return getProcessExecutorConfiguration(getCommandContext());
    }

    public static ProcessExecutorConfiguration getProcessExecutorConfiguration(CommandContext commandContext) {
        return (ProcessExecutorConfiguration)commandContext.getEngineConfiguration();
    }

}
