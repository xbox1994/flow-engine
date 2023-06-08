package com.wty.flowengine.engine.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wty.flowengine.engine.domain.ProcessInstance;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface ProcessInstanceMapper extends BaseMapper<ProcessInstance> {

    int insert(ProcessInstance processInstance);

    List<Long> listExecutionIDByProcDefId(Long procDefId, int limit);

    @Delete("delete from flow_process_instance where process_definition_id = #{arg0}")
    void deleteByProcDefId(Long procDefId);
}

