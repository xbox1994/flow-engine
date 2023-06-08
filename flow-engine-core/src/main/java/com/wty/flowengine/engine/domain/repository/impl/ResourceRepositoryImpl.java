package com.wty.flowengine.engine.domain.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.domain.Resource;
import com.wty.flowengine.engine.domain.mapper.ResourceMapper;
import com.wty.flowengine.engine.domain.repository.ResourceRepository;

public class ResourceRepositoryImpl extends BaseRepository<ResourceMapper> implements ResourceRepository {
    public ResourceRepositoryImpl(ProcessEngineConfiguration processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    public Resource create() {
        Resource resource = new Resource();
        resource.setId(genId());
        return resource;
    }

    @Override
    public void insert(Resource resource) {
        mapper.insert(resource);
    }

    @Override
    public Resource selectByDeploymentIdAndName(Long deploymentId, String resourceName) {
        return createQuery()
                .eq(Resource::getDeploymentId, deploymentId)
                .eq(Resource::getName, resourceName).one();
    }

    @Override
    public void deleteByDeploymentId(Long deploymentId) {
        mapper.deleteByDeploymentId(deploymentId);
    }

    private LambdaQueryChainWrapper<Resource> createQuery() {
        return new LambdaQueryChainWrapper<>(mapper);
    }
}
