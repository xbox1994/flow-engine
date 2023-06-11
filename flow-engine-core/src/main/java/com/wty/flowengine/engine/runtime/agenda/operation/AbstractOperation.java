package com.wty.flowengine.engine.runtime.agenda.operation;

import com.wty.flowengine.rest.demo.test.model.FlowElement;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.runtime.agenda.FlowEngineAgenda;
import com.wty.flowengine.engine.runtime.util.CommandContextUtil;

public abstract class AbstractOperation implements Runnable {

    protected CommandContext commandContext;

    /**
     * 任务队列管理器
     */
    protected FlowEngineAgenda agenda;

    /**
     * 执行令牌
     */
    protected Execution execution;


    public AbstractOperation() {

    }

    public AbstractOperation(CommandContext commandContext, Execution execution) {
        this.commandContext = commandContext;
        this.execution = execution;
        this.agenda = CommandContextUtil.getAgenda(commandContext);
    }

    public FlowElement getCurrentFlowElement() {
        return execution.getCurrentFlowElement();
    }

}
