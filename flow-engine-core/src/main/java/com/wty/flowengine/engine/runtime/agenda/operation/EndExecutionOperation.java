package com.wty.flowengine.engine.runtime.agenda.operation;

import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.runtime.util.CommandContextUtil;

public class EndExecutionOperation extends AbstractOperation {
    public EndExecutionOperation(CommandContext commandContext, Execution execution) {
        super(commandContext, execution);
    }

    /**
     * 执行终止流程的操作
     * 1. 记录流程实例结束事件 <br>
     * 2. 删除 Execution 对象  <br>
     */
    @Override
    public void run() {
        if (execution.getParentId() == null) {
            handleProcessInstanceEnded();
        } else {
            handleExecutionEnded();
        }
    }

    private void handleExecutionEnded() {
        // 删除 execution 和关联的资源，继续执行父流程
        ProcessEngineConfiguration cfg = CommandContextUtil.getProcessEngineConfiguration();
        cfg.getExecutionRepository().delete(execution);
        Execution parentExecution = execution.getParent();
        System.out.println("继续执行parentExecution， 当前节点：" + parentExecution.getCurrentFlowElement());
        agenda.planTakeOutgoingSequenceFlowsOperation(parentExecution, true);
    }

    private void handleProcessInstanceEnded() {
        CommandContextUtil.getProcessEngineConfiguration()
                .getProcessEventLogRepository().recordProcessInstanceEnd(execution);
        // 记录流程实例结束事件
        // 删除 Execution 对象
        // 保留历史变量

    }

}
