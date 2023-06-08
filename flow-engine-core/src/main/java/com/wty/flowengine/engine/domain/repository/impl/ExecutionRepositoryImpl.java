package com.wty.flowengine.engine.domain.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.domain.mapper.ExecutionMapper;
import com.wty.flowengine.engine.domain.repository.ExecutionRepository;

public class ExecutionRepositoryImpl extends BaseRepository<ExecutionMapper> implements ExecutionRepository {

    public ExecutionRepositoryImpl(ProcessEngineConfiguration processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    public Execution create() {
        Execution execution = new Execution();
        execution.setId(genId());
        return execution;
    }

    @Override
    public void insert(Execution execution) {
        mapper.insert(execution);
    }

    @Override
    public void delete(Execution execution) {
        if (execution.getId() == null) {
            throw new FlowEngineException("Delete record failed, entity id is required");
        }
        mapper.deleteById(execution.getId());
    }

    @Override
    public int countByProcDefID(Long id) {
        return mapper.countByProcDefID(id);
    }

    @Override
    public Execution createChildExecution(Execution execution) {
        Execution child = processEngineConfiguration.getExecutionRepository().create();
        if (execution.getProcessDefinition() != null) {
            child.setProcessDefinition(execution.getProcessDefinition());
        }
        child.setParent(execution);
        child.setProcInstanceId(execution.getProcInstanceId());
        return child;
    }

    @Override
    public void save(Execution execution) {
        mapper.save(execution);

    }

    @Override
    public Execution findById(Long executionId) {
        return mapper.selectById(executionId);

    }

    @Override
    public Execution findInstanceByDefIdAndBizId(Long procDefId, String bizId) {
        return createQuery().eq(Execution::getProcDefinitionId, procDefId)
                .eq(Execution::getBizId, bizId)
                .isNull(Execution::getParentId)
                .one();
    }

    private LambdaQueryChainWrapper<Execution> createQuery() {
        return new LambdaQueryChainWrapper<>(mapper);
    }

}
