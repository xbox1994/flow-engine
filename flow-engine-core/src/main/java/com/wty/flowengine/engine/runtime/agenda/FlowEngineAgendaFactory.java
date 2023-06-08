package com.wty.flowengine.engine.runtime.agenda;

import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.common.command.Session;
import com.wty.flowengine.engine.common.command.SessionFactory;

public class FlowEngineAgendaFactory implements SessionFactory {
    @Override
    public Class<?> getSessionType() {
        return FlowEngineAgenda.class;
    }

    @Override
    public Session openSession(CommandContext commandContext) {
        return new FlowEngineAgenda(commandContext);
    }
}
