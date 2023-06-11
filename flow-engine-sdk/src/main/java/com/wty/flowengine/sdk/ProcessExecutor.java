package com.wty.flowengine.sdk;

import com.wty.flowengine.sdk.exec.cmd.StartProcessCmd;
import com.wty.flowengine.rest.demo.test.model.Process;

import java.util.Map;

public class ProcessExecutor {

    private final ProcessExecutorConfiguration configuration;

    public ProcessExecutor(ProcessExecutorConfiguration configuration) {
        this.configuration = configuration;
    }

    public void start(Process process, Map<String, Object> variables) {
        configuration.getCommandExecutor().execute(new StartProcessCmd(process, variables));
    }
}
