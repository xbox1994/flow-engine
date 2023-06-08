package com.wty.flowengine.engine.domain.repository;

import com.wty.flowengine.engine.domain.ProcessInstance;

import java.util.List;

public interface ProcessInstanceRepository {
    ProcessInstance create();

    void insert(ProcessInstance processInstance);

    List<Long> listExecutionIDByProcDefId(Long procDefId, int limit);

    void deleteByProcDefId(Long procDefId);
}
