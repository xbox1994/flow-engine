package com.wty.flowengine.engine.runtime.cmd;

import com.google.common.base.Strings;
import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.common.command.Command;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.domain.ProcessDefinition;
import com.wty.flowengine.engine.runtime.util.CommandContextUtil;
import org.springframework.transaction.support.TransactionTemplate;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * 删除部署及关联的数据，包括流程定义、流程实例
 */
public class DeleteDeploymentCmd implements Command<Void> {
    private Long deploymentId;
    private String procDefKey;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long deploymentId;
        private String procDefKey;

        public Builder deploymentId(long deploymentId) {
            this.deploymentId = deploymentId;
            return this;
        }

        public Builder procDefKey(String procDefKey) {
            this.procDefKey = procDefKey;
            return this;
        }

        public DeleteDeploymentCmd build() {
            if (deploymentId == null && procDefKey == null) {
                throw new InvalidParameterException("必须指定deploymentID和procDefKey中的一个");
            }
            if (deploymentId != null && procDefKey != null) {
                throw new InvalidParameterException("只能指定deploymentID和procDefKey中的一个");
            }
            DeleteDeploymentCmd cmd = new DeleteDeploymentCmd();
            cmd.deploymentId = deploymentId;
            cmd.procDefKey = procDefKey;
            return cmd;
        }
    }

    private DeleteDeploymentCmd() {
    }

    @Override
    public Void execute(CommandContext commandContext) {
        ProcessEngineConfiguration config = CommandContextUtil.getProcessEngineConfiguration();
        if (deploymentId != null) {
            deleteByDeploymentId(config, deploymentId);
        } else if (!Strings.isNullOrEmpty(procDefKey)) {
            deleteByProcDefKey(config);
        }
        return null;
    }

    private void deleteByProcDefKey(ProcessEngineConfiguration config) {
        List<Long> deploymentIds = config.getProcessDefinitionRepository()
                .listDeploymentIdByProcDefKey(this.procDefKey);
        for (Long deploymentId : deploymentIds) {
            deleteByDeploymentId(config, deploymentId);
        }
    }

    private void deleteByDeploymentId(ProcessEngineConfiguration config, Long deploymentId) {
        // 查询、校验、准备数据
        ProcessDefinition procDefinition = config.getProcessDefinitionRepository().findByDeploymentId(deploymentId);
        if (procDefinition == null) {
            throw new FlowEngineException("流程定义不存在");
        }
        List<Long> executionIDs = config.getProcessInstanceRepository().listExecutionIDByProcDefId(procDefinition.getId(), 100);
        if (executionIDs.size() == 100) {
            throw new FlowEngineException("流程实例数超过100，不允许删除流程定义");
        }
        // 检查正在运行的流程实例
        int count = config.getExecutionRepository().countByProcDefID(procDefinition.getId());
        if (count > 0) {
            throw new FlowEngineException("存在运行中的流程实例，不允许删除流程定义");
        }
        TransactionTemplate transactionTemplate = new TransactionTemplate(config.getTransactionManager()); // note 不支持事务嵌套
        transactionTemplate.executeWithoutResult(txStatus -> {
            config.getDeploymentRepository().deleteById(deploymentId);
            config.getProcessDefinitionRepository().deleteByDeploymentId(deploymentId);
            config.getProcessInstanceRepository().deleteByProcDefId(procDefinition.getId());
            config.getResourceRepository().deleteByDeploymentId(deploymentId);
        });
    }
}
