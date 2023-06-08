package com.wty.flowengine.engine.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wty.flowengine.engine.domain.Execution;

public interface ExecutionMapper extends BaseMapper<Execution> {
    int insert(Execution execution);

    int countByProcDefID(Long id);
    void save(Execution execution);
}
