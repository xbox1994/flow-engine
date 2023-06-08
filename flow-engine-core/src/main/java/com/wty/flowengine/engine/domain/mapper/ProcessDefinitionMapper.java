package com.wty.flowengine.engine.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wty.flowengine.engine.domain.ProcessDefinition;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProcessDefinitionMapper extends BaseMapper<ProcessDefinition> {
    int insert(ProcessDefinition processDefinition);

    ProcessDefinition findLatestByKey(String key);

    int deleteByDeploymentId(Long deploymentId);

    @Select("select deployment_id from flow_process_definition where `key` = #{arg0}")
    List<Long> listDeploymentIdByProcDefKey(String procDefKey);
}
