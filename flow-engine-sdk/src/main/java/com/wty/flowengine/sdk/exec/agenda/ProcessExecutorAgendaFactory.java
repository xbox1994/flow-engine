package com.wty.flowengine.sdk.exec.agenda;

import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.common.command.Session;
import com.wty.flowengine.engine.common.command.SessionFactory;

public class ProcessExecutorAgendaFactory implements SessionFactory {
    @Override
    public Class<?> getSessionType() {
        return ProcessExecutorAgenda.class;
    }

    @Override
    public Session openSession(CommandContext commandContext) {
        return new ProcessExecutorAgenda(commandContext);
    }
}
