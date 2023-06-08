package com.wty.flowengine.engine.common.command;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultCommandExecutor implements CommandExecutor {

    private final ExecutorService executorService;

    private final CommandInterceptor first;

    @Override
    public <T> T execute(Command<T> command) {
        return first.execute(command, this);
    }

    public DefaultCommandExecutor(CommandInterceptor first) {
        this.first = first;
        this.executorService = Executors.newFixedThreadPool(1000);
    }

    public void executeAsync(Command<?> command) {
        executorService.execute(() -> execute(command));
    }
}
