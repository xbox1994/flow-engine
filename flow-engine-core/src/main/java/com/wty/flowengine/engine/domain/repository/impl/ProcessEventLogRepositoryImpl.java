package com.wty.flowengine.engine.domain.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.wty.flow.model.FlowElement;
import com.wty.flow.model.FlowNode;
import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.domain.ActivityTask;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.domain.ProcessEventLog;
import com.wty.flowengine.engine.domain.mapper.ProcessEventLogMapper;
import com.wty.flowengine.engine.domain.repository.ProcessEventLogRepository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.List;

public class ProcessEventLogRepositoryImpl extends BaseRepository<ProcessEventLogMapper> implements ProcessEventLogRepository {


    public ProcessEventLogRepositoryImpl(ProcessEngineConfiguration processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    public ProcessEventLog create() {
        ProcessEventLog log = new ProcessEventLog();
        log.setId(genId());
        log.setTime(new Date());
        return log;
    }

    @Override
    public void insert(ProcessEventLog processEventLog) {
        mapper.insert(processEventLog);
    }

    @Override
    public void recordProcessInstanceStart(Execution execution) {
        ProcessEventLog processEventLog = createProcessLog(execution, ProcessEventLog.Event.PROCESS_STARTED);
        new TransactionTemplate(processEngineConfiguration.getTransactionManager()).executeWithoutResult(status -> {
            processEngineConfiguration.getExecutionRepository().insert(execution);
            insert(processEventLog);
        });
    }

    @Nonnull
    private ProcessEventLog createProcessLog(Execution execution, String event) {
        ProcessEventLog processEventLog = create();
        if (execution.getParent() != null) {
            throw new RuntimeException("Program error, parent execution should be null");
        }
        processEventLog.setProcInstId(execution.getId());
        processEventLog.setExecutionId(execution.getId());
        processEventLog.setEvent(event);
        processEventLog.setVariables(execution.getScopeVariables());
        return processEventLog;
    }

    @Override
    public void recordActivityStart(Execution execution, FlowNode flowNode) {
        ProcessEventLog processEventLog = createActivityLog(execution, ProcessEventLog.Event.ACTIVITY_STARTED, flowNode);
        insert(processEventLog);
    }

    @Override
    public void recordActivityEnd(Execution execution, FlowNode flowNode) {
        ProcessEventLog processEventLog = createActivityLog(execution, ProcessEventLog.Event.ACTIVITY_ENDED, flowNode);
        insert(processEventLog);

    }

    @Nonnull
    private ProcessEventLog createActivityLog(Execution execution, String activityEnded, FlowElement flowNode) {
        ProcessEventLog processEventLog = create();
        processEventLog.setProcInstId(execution.getProcInstanceId());
        processEventLog.setExecutionId(execution.getId());
        processEventLog.setEvent(activityEnded);
        processEventLog.setNodeId(flowNode.getId());
        processEventLog.setNodeName(flowNode.getName());
        processEventLog.setVariables(execution.getScopeVariables());
        return processEventLog;
    }

    @Override
    public void recordProcessInstanceEnd(Execution execution) {
        ProcessEventLog processLog = createProcessLog(execution, ProcessEventLog.Event.PROCESS_ENDED);
        processLog.setNodeId(execution.getCurrentFlowElement().getId());
        processLog.setNodeName(execution.getCurrentFlowElement().getName());
        insert(processLog); // todo 是否删除实例数据(Execution，或者创建一个ProcessInstance
    }

    @Override
    public List<ProcessEventLog> listByExecutionId(Long processInstanceId) {
        LambdaQueryChainWrapper<ProcessEventLog> queryChainWrapper = new LambdaQueryChainWrapper<>(mapper);
        return queryChainWrapper.eq(ProcessEventLog::getExecutionId, processInstanceId).list();

    }

    @Override
    public void recordActivityFailure(Execution execution, Exception e) {
        ProcessEventLog processEventLog = createActivityLog(execution, ProcessEventLog.Event.EXECUTION_FAILED, execution.getCurrentFlowElement());
        processEventLog.setException(e.getMessage());
        insert(processEventLog);
        processEngineConfiguration.getExecutionRepository().save(execution);
    }

    @Override
    public void recordActivityTaskCreated(Execution execution, ActivityTask task) {
        ProcessEventLog processEventLog = createActivityLog(execution, ProcessEventLog.Event.TASK_CREATED, execution.getCurrentFlowElement());
        new TransactionTemplate(processEngineConfiguration.getTransactionManager()).executeWithoutResult(status -> {
            processEngineConfiguration.getExecutionRepository().save(execution);
            processEngineConfiguration.getActivityTaskRepository().save(task);
            insert(processEventLog);
        });
    }
}
