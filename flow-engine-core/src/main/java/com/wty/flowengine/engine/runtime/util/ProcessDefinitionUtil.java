package com.wty.flowengine.engine.runtime.util;

import com.wty.flow.model.FlowElement;
import com.wty.flow.model.FlowNode;
import com.wty.flow.model.Process;
import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.domain.ProcessDefinition;

import java.util.List;

public class ProcessDefinitionUtil {
    public static ProcessDefinition resolveProcessDefinition(ProcessDefinition processDefinition) {
        Process process = processDefinition.getProcess();
        // parse process
        List<FlowElement> flowElements = process.getFlowElements();
        ProcessEngineConfiguration configuration = CommandContextUtil.getProcessEngineConfiguration();
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof FlowNode) {
                Object behavior = configuration.getActivityBehaviorFactory().create(flowElement);
                ((FlowNode) flowElement).setBehavior(behavior);
            }
        }
        return processDefinition;
    }

    public static ProcessDefinition getProcessDefinition(Long procDefinitionId) {
        // todo cache
        ProcessEngineConfiguration configuration = CommandContextUtil.getProcessEngineConfiguration();
        ProcessDefinition processDefinition = configuration.getProcessDefinitionRepository().findById(procDefinitionId);
        return ProcessDefinitionUtil.resolveProcessDefinition(processDefinition);
    }

    public static ProcessDefinition getProcessDefinitionByKey(String processKey) {
        ProcessEngineConfiguration configuration = CommandContextUtil.getProcessEngineConfiguration();
        ProcessDefinition processDefinition = configuration.getProcessDefinitionRepository().findByKey(processKey);
        return ProcessDefinitionUtil.resolveProcessDefinition(processDefinition);
    }
}
