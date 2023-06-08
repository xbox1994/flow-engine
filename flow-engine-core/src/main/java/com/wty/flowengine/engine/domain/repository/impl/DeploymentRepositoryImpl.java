package com.wty.flowengine.engine.domain.repository.impl;

import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.domain.Deployment;
import com.wty.flowengine.engine.domain.mapper.DeploymentMapper;
import com.wty.flowengine.engine.domain.repository.DeploymentRepository;

import java.util.Date;

public class DeploymentRepositoryImpl extends BaseRepository<DeploymentMapper> implements DeploymentRepository {

    public DeploymentRepositoryImpl(ProcessEngineConfiguration processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    public Deployment create() {
        Deployment deployment = new Deployment();
        deployment.setId(genId()); // todo 提取 Entity 接口
        deployment.setDeployTime(new Date());
        return deployment;
    }

    @Override
    public void insert(Deployment deployment) {
        mapper.insert(deployment);
    }

    @Override
    public void deleteById(Long deploymentId) {
        mapper.deleteById(deploymentId);
    }

}
