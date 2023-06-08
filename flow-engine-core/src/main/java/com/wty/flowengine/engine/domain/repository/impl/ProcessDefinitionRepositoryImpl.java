package com.wty.flowengine.engine.domain.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.domain.ProcessDefinition;
import com.wty.flowengine.engine.domain.mapper.ProcessDefinitionMapper;
import com.wty.flowengine.engine.domain.repository.ProcessDefinitionRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ProcessDefinitionRepositoryImpl extends BaseRepository<ProcessDefinitionMapper> implements ProcessDefinitionRepository {

    public ProcessDefinitionRepositoryImpl(ProcessEngineConfiguration processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Nonnull
    @Override
    public ProcessDefinition findByKey(String key) {
        ProcessDefinition result = mapper.findLatestByKey(key);
        if (result == null) {
            throw new FlowEngineException("流程定义未找到，key = " + key);
        }
        return result;
    }

    @Override
    public ProcessDefinition create() {
        ProcessDefinition processDefinition = new ProcessDefinition();
        processDefinition.setId(genId());
        return processDefinition;
    }

    @Nullable
    @Override
    public ProcessDefinition getDerivedProcessDefinition(ProcessDefinition processDefinition) {
        return mapper.findLatestByKey(processDefinition.getKey());
    }

    @Override
    public void insert(ProcessDefinition processDefinition) {
        mapper.insert(processDefinition);
    }

    @Override
    public ProcessDefinition findById(Long procDefId) {
        return mapper.selectById(procDefId);
    }

    @Override
    public int deleteByDeploymentId(Long deploymentId) {
        return mapper.deleteByDeploymentId(deploymentId);
    }

    @Nullable
    @Override
    public ProcessDefinition findByDeploymentId(Long deploymentId) {
        return createQuery().eq(ProcessDefinition::getDeploymentId, deploymentId).one();
    }

    @Override
    public List<Long> listDeploymentIdByProcDefKey(String procDefKey) {
        return mapper.listDeploymentIdByProcDefKey(procDefKey);
    }

    private LambdaQueryChainWrapper<ProcessDefinition> createQuery() {
        return new LambdaQueryChainWrapper<>(mapper);
    }


}
