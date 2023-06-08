package com.wty.flowengine.engine.common;

import com.wty.flowengine.engine.common.agenda.Agenda;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.common.command.CommandContext;

import java.util.LinkedList;

public abstract class AbstractAgenda implements Agenda {

    protected final CommandContext commandContext;

    protected final LinkedList<Runnable> operations = new LinkedList<>();

    public AbstractAgenda(CommandContext commandContext) {
        this.commandContext = commandContext;
    }

    @Override
    public void planOperation(Runnable operation) {
        operations.add(operation);
    }

    @Override
    public boolean isEmpty() {
        return operations.isEmpty();
    }

    @Override
    public Runnable getNextOperation() {
        if (operations.isEmpty()) {
            throw new FlowEngineException("Unable to peek empty agenda.");
        }
        return operations.poll();
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() {

    }

    public CommandContext getCommandContext() {
        return commandContext;
    }
}
