package com.wty.flowengine.engine.domain.repository;

import com.wty.flowengine.engine.domain.ActivityTask;
import com.wty.flowengine.engine.domain.mapper.ActivityTaskMapper;

import java.util.List;

public interface ActivityTaskRepository {

    ActivityTask create();


    List<ActivityTask> listByProcInstId(long procInstId);

    void save(ActivityTask activityTask);

    ActivityTask findById(long id);

    ActivityTask findByProcInstAndNode(Long instId, String nodeId);

    ActivityTaskMapper getMapper();
}
