package com.wty.flowengine.engine.common.command;

public interface Command<T> {

    T execute(CommandContext commandContext);
}
