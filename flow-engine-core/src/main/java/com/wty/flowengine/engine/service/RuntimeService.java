package com.wty.flowengine.engine.service;


import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.runtime.cmd.ContinueExecutionCmd;
import com.wty.flowengine.engine.runtime.cmd.StartProcessInstanceCmd;

import javax.annotation.Nullable;
import java.util.Map;

public class RuntimeService {

    private final ProcessEngineConfiguration configuration;

    public RuntimeService(ProcessEngineConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * 根据流程定义的 key 发起一个流程实例。如果流程定义存在多个版本，将选择使用最新的版本。
     *
     * @param procDefKey 流程定义的key
     * @param variables  流程参数
     * @return 流程实例
     */
    public Execution startByKey(String procDefKey, @Nullable Map<String, Object> variables) {
        return configuration.getCommandExecutor()
                .execute(new StartProcessInstanceCmd(procDefKey, null, variables));
    }

    public Execution startByKey(String procDefKey, String bizId, @Nullable Map<String, Object> variables) { // todo (procDefKey, bizId) 建立唯一索引
        return configuration.getCommandExecutor()
                .execute(StartProcessInstanceCmd.builder()
                        .procDefKey(procDefKey)
                        .bizId(bizId)
                        .variables(variables)
                        .build());
    }

    /**
     * 根据流程定义的 id 发起一个流程实例。
     */
    public Execution startProcessById(Long procDefId, @Nullable Map<String, Object> variables) {
        return configuration.getCommandExecutor()
                .execute(new StartProcessInstanceCmd(null, procDefId, variables));
    }

    public void continueExecution(Long failedExecutionId, Map<String, Object> variables) {
        configuration.getCommandExecutor()
                .execute(new ContinueExecutionCmd(failedExecutionId, variables));
    }

}
