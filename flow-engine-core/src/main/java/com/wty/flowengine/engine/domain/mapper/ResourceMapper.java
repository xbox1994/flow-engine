package com.wty.flowengine.engine.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wty.flowengine.engine.domain.Resource;

public interface ResourceMapper extends BaseMapper<Resource> {
    int insert(Resource resource);

    void deleteByDeploymentId(Long deploymentId);
}
