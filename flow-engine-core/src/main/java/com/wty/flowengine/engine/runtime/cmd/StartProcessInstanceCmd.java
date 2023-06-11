package com.wty.flowengine.engine.runtime.cmd;

import com.wty.flowengine.rest.demo.test.model.FlowElement;
import com.wty.flowengine.rest.demo.test.model.Process;
import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.common.command.Command;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.domain.ProcessDefinition;
import com.wty.flowengine.engine.runtime.util.CommandContextUtil;
import com.wty.flowengine.engine.runtime.util.ProcessDefinitionUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class StartProcessInstanceCmd implements Command<Execution> {

    private String procDefKey;
    private Long procDefId;
    private Map<String, Object> variables;

    private String bizId;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String procDefKey;
        private Long procDefId;
        private Map<String, Object> variables;
        private String bizId;

        public Builder procDefKey(String procDefKey) {
            this.procDefKey = procDefKey;
            return this;
        }

        public Builder procDefId(Long procDefId) {
            this.procDefId = procDefId;
            return this;
        }

        public Builder variables(Map<String, Object> variables) {
            this.variables = variables;
            return this;
        }

        public Builder bizId(String bizId) {
            this.bizId = bizId;
            return this;
        }

        public StartProcessInstanceCmd build() {
            StartProcessInstanceCmd cmd = new StartProcessInstanceCmd(procDefKey, procDefId, variables);
            cmd.bizId = bizId;
            return cmd;
        }
    }

    public StartProcessInstanceCmd() {
    }

    public StartProcessInstanceCmd(@Nullable String procDefKey, @Nullable Long procDefId,
                                   @Nullable Map<String, Object> variables) {
        this.procDefKey = procDefKey;
        this.procDefId = procDefId;
        this.variables = variables != null ? variables : new HashMap<>();
    }

    /**
     * 发起流程实例，返回流程实例的执行对象
     */
    @Override
    public Execution execute(CommandContext commandContext) {
        ProcessEngineConfiguration processEngineConfiguration = CommandContextUtil.getProcessEngineConfiguration(commandContext);
        if ((procDefId == null && procDefKey == null) || (procDefId != null && procDefKey != null)) {
            throw new FlowEngineException("Invalid StartProcessCmd");
        }
        ProcessDefinition procDefinition;
        if (procDefId != null) {
            procDefinition = processEngineConfiguration.getProcessDefinitionRepository()
                    .findById(procDefId);
        } else {
            procDefinition = processEngineConfiguration.getProcessDefinitionRepository()
                    .findByKey(procDefKey);
        }

        return startProcessInstance(procDefinition);
    }


    private Execution startProcessInstance(@Nonnull ProcessDefinition processDefinition) {
        ProcessEngineConfiguration configuration = CommandContextUtil.getProcessEngineConfiguration();
        Process process = ProcessDefinitionUtil.resolveProcessDefinition(processDefinition).getProcess();
        FlowElement start = process.getStart();
        // 创建表示流程执行的执行对象
        Execution instance = configuration.getExecutionRepository().create();
        instance.setProcInstanceId(instance.getId());
        instance.setActivityId(start.getId());
        instance.setActivityName(start.getName());
        instance.setCurrentFlowElement(start);
        instance.setProcessDefinition(processDefinition);
        instance.setVariables(variables);
        instance.setBizId(bizId);

        // 记录流程实例开始
        configuration.getProcessEventLogRepository().recordProcessInstanceStart(instance);
        configuration.getCommandExecutor().executeAsync(commandContext -> {
            // 创建第一个 child instance 并开始执行流程
            // 创建执行实例，并开始执行
            Execution execution = configuration.getExecutionRepository().createChildExecution(instance);
            execution.setCurrentFlowElement(start);
            CommandContextUtil.getAgenda().planContinueProcessOperation(instance);
            return null;
        });
        return instance;
    }


}
