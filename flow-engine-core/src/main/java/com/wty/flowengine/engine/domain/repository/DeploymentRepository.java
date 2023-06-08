package com.wty.flowengine.engine.domain.repository;

import com.wty.flowengine.engine.domain.Deployment;

public interface DeploymentRepository {

    Deployment create();

    void insert(Deployment deployment);

    void deleteById(Long deploymentId);

}
