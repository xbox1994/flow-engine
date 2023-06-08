package com.wty.flowengine.engine.common.command;

public interface CommandInterceptor {
    <T> T execute(Command<T> command, CommandExecutor commandExecutor);

    CommandInterceptor getNext();

    void setNext(CommandInterceptor next);
}
