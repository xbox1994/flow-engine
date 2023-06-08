package com.wty.flowengine.engine.service;


import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.domain.ActivityTask;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.domain.ProcessDefinition;
import com.wty.flowengine.engine.runtime.cmd.CompleteActivityTaskCmd;

import java.util.List;
import java.util.Map;

public class TaskService {

    private final ProcessEngineConfiguration configuration;

    public TaskService(ProcessEngineConfiguration configuration) {
        this.configuration = configuration;
    }

    public List<ActivityTask> listActivityTasks(Long procInstId) {
        return configuration.getActivityTaskRepository().listByProcInstId(procInstId);
    }

    public void complete(long taskId, Map<String, Object> variables) {
        ActivityTask task = configuration.getActivityTaskRepository().findById(taskId);
        if (task == null) {
            throw new FlowEngineException("ActivityTask not found, taskId=" + taskId);
        }
        configuration.getCommandExecutor().execute(new CompleteActivityTaskCmd(task, variables));
    }

    public void complete(String procKey, String bizId, String nodeId, Map<String, Object> variables) {
        ActivityTask task = findTask(procKey, bizId, nodeId);
        if (task == null) {
            throw new FlowEngineException("ActivityTask not found, bizId = " + bizId + ", nodeId=" + nodeId);
        }
        configuration.getCommandExecutor().execute(new CompleteActivityTaskCmd(task, variables));
    }

    public ActivityTask findTask(String procKey, String bizId, String nodeId) {
        ProcessDefinition processDefinition = configuration.getProcessDefinitionRepository().findByKey(procKey);
        if (processDefinition == null) {
            throw new FlowEngineException("ProcessDefinition not found, procKey=" + procKey);
        }
        Execution instance = configuration.getExecutionRepository().findInstanceByDefIdAndBizId(processDefinition.getId(), bizId);
        if (instance == null) {
            throw new FlowEngineException("Execution not found, procKey= " + procKey + ", bizId=" + bizId);
        }
        ActivityTask task = configuration.getActivityTaskRepository().findByProcInstAndNode(instance.getId(), nodeId);
        if (task == null) {
            throw new FlowEngineException("ActivityTask not found, instanceId = " + instance.getId() + ", nodeId=" + nodeId);
        }
        return task;
    }
}
