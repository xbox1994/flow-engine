package com.wty.flowengine.engine.domain.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.domain.ActivityTask;
import com.wty.flowengine.engine.domain.mapper.ActivityTaskMapper;
import com.wty.flowengine.engine.domain.repository.ActivityTaskRepository;

import java.util.List;

public class ActivityTaskRepositoryImpl extends BaseRepository<ActivityTaskMapper> implements ActivityTaskRepository {

    public ActivityTaskRepositoryImpl(ProcessEngineConfiguration processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    public ActivityTask create() {
        ActivityTask task = new ActivityTask();
        task.setId(genId()); // todo 提取 Entity 接口
        return task;
    }

    @Override
    public List<ActivityTask> listByProcInstId(long procInstId) {
        return createQuery().eq(ActivityTask::getProcInstId, procInstId).list();
    }

    @Override
    public void save(ActivityTask activityTask) {
        mapper.save(activityTask);
    }

    @Override
    public ActivityTask findById(long id) {
        return mapper.selectById(id);
    }

    @Override
    public ActivityTask findByProcInstAndNode(Long instId, String nodeId) {
        return createQuery()
                .eq(ActivityTask::getProcInstId, instId)
                .eq(ActivityTask::getActivityId, nodeId)
                .one();
    }

    @Override
    public ActivityTaskMapper getMapper() {
        return mapper;
    }

    private LambdaQueryChainWrapper<ActivityTask> createQuery() {
        return new LambdaQueryChainWrapper<>(mapper);
    }


}
