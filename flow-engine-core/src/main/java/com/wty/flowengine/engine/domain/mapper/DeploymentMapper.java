package com.wty.flowengine.engine.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wty.flowengine.engine.domain.Deployment;

public interface DeploymentMapper extends BaseMapper<Deployment> {
    int insert(Deployment deployment);
}
