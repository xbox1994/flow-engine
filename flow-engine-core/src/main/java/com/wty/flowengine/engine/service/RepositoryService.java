package com.wty.flowengine.engine.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.domain.Deployment;
import com.wty.flowengine.engine.domain.repository.ProcessDefinitionRepository;
import com.wty.flowengine.engine.runtime.cmd.DeleteDeploymentCmd;
import com.wty.flowengine.engine.runtime.cmd.DeployCmd;
import lombok.Data;

@Data
public class RepositoryService {

    private ProcessEngineConfiguration configuration;

    public RepositoryService(ProcessEngineConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * 部署流程定义。多次部署同一流程定义，版本号会递增
     *
     * @param jsonNode 流程定义
     * @return 部署记录
     */
    public Deployment deploy(JsonNode jsonNode) {
        return configuration.getCommandExecutor().execute(new DeployCmd(jsonNode));
    }

    /**
     * 删除部署。会删除掉所有关联的数据。
     *
     * @param deploymentId 创建部署时返回的部署ID
     */
    public void deleteDeployment(Long deploymentId) {
        configuration.getCommandExecutor().execute(
                DeleteDeploymentCmd.builder()
                        .deploymentId(deploymentId)
                        .build());
    }


    /**
     * 删除流程定义。同时删除所有的关联数据。
     *
     * @param key 流程定义的key
     */
    public void deleteProcessDefinition(String key) {
        configuration.getCommandExecutor().execute(
                DeleteDeploymentCmd.builder()
                        .procDefKey(key)
                        .build());
    }

    public void listProcessDefinitions() {

        ProcessDefinitionRepository repository = configuration.getProcessDefinitionRepository();

    }




}
