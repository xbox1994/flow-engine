package com.wty.flowengine.engine.common.command;

public interface CommandExecutor {
    <T> T execute(Command<T> command);
}
