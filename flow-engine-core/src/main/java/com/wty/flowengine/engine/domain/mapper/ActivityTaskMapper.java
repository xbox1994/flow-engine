package com.wty.flowengine.engine.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wty.flowengine.engine.domain.ActivityTask;


public interface ActivityTaskMapper extends BaseMapper<ActivityTask> {
    void save(ActivityTask activityTask);

}
