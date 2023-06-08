package com.wty.flowengine.engine.domain.repository;

import com.wty.flowengine.engine.domain.ProcessDefinition;

import javax.annotation.Nullable;
import java.util.List;

public interface ProcessDefinitionRepository {

    ProcessDefinition findByKey(String key);

    /**
     * 创建一个实例，包含了ID和其他默认属性，不会持久化
     */
    ProcessDefinition create();

    /**
     * 获取最近部署的流程定义
     */
    @Nullable
    ProcessDefinition getDerivedProcessDefinition(ProcessDefinition processDefinition);


    void insert(ProcessDefinition processDefinition);

    ProcessDefinition findById(Long procDefId);

    int deleteByDeploymentId(Long deploymentId);

    @Nullable
    ProcessDefinition findByDeploymentId(Long deploymentId);

    List<Long> listDeploymentIdByProcDefKey(String procDefKey);
}
