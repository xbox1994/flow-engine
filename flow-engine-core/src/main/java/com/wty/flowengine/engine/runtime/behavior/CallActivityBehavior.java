package com.wty.flowengine.engine.runtime.behavior;

import com.wty.flowengine.rest.demo.test.model.CallActivity;
import com.wty.flowengine.rest.demo.test.model.FlowElement;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.domain.ProcessDefinition;
import com.wty.flowengine.engine.runtime.util.CommandContextUtil;
import com.wty.flowengine.engine.runtime.util.ProcessDefinitionUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CallActivityBehavior extends FlowNodeActivityBehavior {
    @Override
    public void execute(Execution execution) {
        CallActivity callActivity = (CallActivity) execution.getCurrentFlowElement();
        String processId = callActivity.getProcessId();
        log.debug("执行「调用子流程」节点：" + execution.getCurrentFlowElement() + "，调用的子流程id：" + processId);
        // 创建子execution执行子流程
        // 根据子流程id获取子流程的第一个节点
        ProcessDefinition processDefinition = ProcessDefinitionUtil.getProcessDefinitionByKey(processId);
        FlowElement startElement = processDefinition.getProcess().getStart();
        Execution childExecution = CommandContextUtil.getProcessEngineConfiguration().getExecutionRepository()
                .createChildExecution(execution);
        childExecution.setCurrentFlowElement(startElement);
        CommandContextUtil.getAgenda().planContinueProcessOperation(childExecution);
    }
}
