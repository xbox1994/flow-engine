package com.wty.flowengine.engine.domain.repository;

import com.wty.flowengine.engine.domain.Resource;

public interface ResourceRepository {
    Resource create();

    void insert(Resource resource);

    Resource selectByDeploymentIdAndName(Long deploymentId, String resourceName);

    void deleteByDeploymentId(Long deploymentId);
}
