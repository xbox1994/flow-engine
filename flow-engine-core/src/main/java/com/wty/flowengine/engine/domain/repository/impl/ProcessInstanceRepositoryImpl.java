package com.wty.flowengine.engine.domain.repository.impl;

import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.domain.ProcessInstance;
import com.wty.flowengine.engine.domain.mapper.ProcessInstanceMapper;
import com.wty.flowengine.engine.domain.repository.ProcessInstanceRepository;

import java.util.List;

public class ProcessInstanceRepositoryImpl extends BaseRepository<ProcessInstanceMapper> implements ProcessInstanceRepository {


    public ProcessInstanceRepositoryImpl(ProcessEngineConfiguration processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    public ProcessInstance create() {
        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setId(genId());
        return processInstance;
    }

    @Override
    public void insert(ProcessInstance processInstance) {
        mapper.insert(processInstance);
    }

    @Override
    public List<Long> listExecutionIDByProcDefId(Long procDefId, int limit) {
        return mapper.listExecutionIDByProcDefId(procDefId, limit);
    }

    @Override
    public void deleteByProcDefId(Long procDefId) {
        mapper.deleteByProcDefId(procDefId);
    }
}
