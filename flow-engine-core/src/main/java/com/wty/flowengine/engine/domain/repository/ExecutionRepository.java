package com.wty.flowengine.engine.domain.repository;

import com.wty.flowengine.engine.domain.Execution;

public interface ExecutionRepository {

    Execution create();

    void insert(Execution execution);

    void delete(Execution execution);

    int countByProcDefID(Long id);

    Execution createChildExecution(Execution instance);

    void save(Execution execution);

    Execution findById(Long executionId);

    Execution findInstanceByDefIdAndBizId(Long prcDefId, String bizId);
}
