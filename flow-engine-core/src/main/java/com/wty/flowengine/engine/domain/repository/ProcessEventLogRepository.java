package com.wty.flowengine.engine.domain.repository;

import com.wty.flowengine.rest.demo.test.model.FlowNode;
import com.wty.flowengine.engine.domain.ActivityTask;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.domain.ProcessEventLog;

import java.util.List;

public interface ProcessEventLogRepository {

    ProcessEventLog create();

    void insert(ProcessEventLog processEventLog);
    /**
     * 保存流程实例并记录流程实例的开始事件
     *
     * @param execution 代表流程执行的执行对象
     */
    void recordProcessInstanceStart(Execution execution);

    /**
     * 记录流程活动节点开始事件
     *
     * @param execution 代表执行节点的执行对象
     * @param flowNode  执行的节点
     */
    void recordActivityStart(Execution execution, FlowNode flowNode);

    /**
     * 记录流程活动节点执行结束事件
     *
     * @param execution 代表执行节点的执行对象
     * @param flowNode  执行的节点
     */
    void recordActivityEnd(Execution execution, FlowNode flowNode);

    void recordProcessInstanceEnd(Execution execution);

    List<ProcessEventLog> listByExecutionId(Long processInstanceId);

    void recordActivityFailure(Execution execution, Exception e);

    void recordActivityTaskCreated(Execution execution, ActivityTask task);
}
