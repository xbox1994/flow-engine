package com.wty.flowengine.engine.runtime.cmd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.wty.flowengine.rest.demo.test.model.Process;
import com.wty.flowengine.converter.ProcessConverter;
import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.common.command.Command;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.domain.Deployment;
import com.wty.flowengine.engine.domain.ProcessDefinition;
import com.wty.flowengine.engine.domain.Resource;
import com.wty.flowengine.engine.domain.repository.ProcessDefinitionRepository;
import com.wty.flowengine.engine.runtime.util.CommandContextUtil;
import org.springframework.transaction.support.TransactionTemplate;

public class DeployCmd implements Command<Deployment> {

    /**
     * 流程定义
     */
    private final JsonNode jsonNode;

    public DeployCmd(JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }

    private static class DeploymentContext {
        ProcessEngineConfiguration configuration;
        Process process;
        Resource resource;
        ProcessDefinition processDefinition;
        Deployment deployment;
    }

    @Override
    public Deployment execute(CommandContext commandContext) {
        ProcessEngineConfiguration configuration = CommandContextUtil.getProcessEngineConfiguration(commandContext);
        DeploymentContext deploymentContext = new DeploymentContext();
        deploymentContext.configuration = configuration;
        prepareDeployment(deploymentContext);
        prepareResource(deploymentContext);
        prepareProcessDefinition(deploymentContext);
        persist(deploymentContext);
        return deploymentContext.deployment;
    }

    private void prepareDeployment(DeploymentContext context) {
        context.deployment = context.configuration.getDeploymentRepository().create();
    }

    private void prepareResource(DeploymentContext context) {
        Resource resource = context.configuration.getResourceRepository().create();
        Process process = ProcessConverter.convertToProcess(jsonNode);
        resource.setName(process.getName() + ".json");
        try {
            resource.setBytes(context.configuration.getObjectMapper().writeValueAsBytes(jsonNode));
        } catch (JsonProcessingException e) {
            throw new FlowEngineException(e);
        }
        resource.setDeploymentId(context.deployment.getId());
        context.resource = resource;
        context.process = process;
    }


    private void prepareProcessDefinition(DeploymentContext context) {
        ProcessDefinitionRepository processDefinitionRepository = context.configuration.getProcessDefinitionRepository();
        ProcessDefinition processDefinition = processDefinitionRepository.create();
        processDefinition.setName(context.process.getName());
        processDefinition.setKey(context.process.getId());
        processDefinition.setResourceName(context.resource.getName());
        processDefinition.setProcess(context.process);
        processDefinition.setDeploymentId(context.deployment.getId());

        // 计算version
        ProcessDefinition prev = processDefinitionRepository.getDerivedProcessDefinition(processDefinition);
        int version = prev != null ? prev.getVersion() + 1 : 0;
        processDefinition.setVersion(version);
        context.processDefinition = processDefinition;
    }

    void persist(DeploymentContext context) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(context.configuration.getTransactionManager());
        transactionTemplate.execute(txStatus->{
            context.configuration.getDeploymentRepository().insert(context.deployment);
            context.configuration.getProcessDefinitionRepository().insert(context.processDefinition);
            context.configuration.getResourceRepository().insert(context.resource);
            return null;
        });

    }


}
