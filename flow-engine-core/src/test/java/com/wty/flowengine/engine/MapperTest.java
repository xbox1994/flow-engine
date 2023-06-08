package com.wty.flowengine.engine;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.wty.flowengine.engine.common.utils.JsonUtil;
import com.wty.flowengine.engine.domain.ActivityTask;
import com.wty.flowengine.engine.domain.ProcessDefinition;
import com.wty.flowengine.engine.domain.mapper.ActivityTaskMapper;
import com.wty.flowengine.engine.domain.mapper.ProcessDefinitionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {TestConfiguration.class})
public class MapperTest {

    @Resource
    ProcessDefinitionMapper processDefinitionMapper;

    @Resource
    ActivityTaskMapper activityTaskMapper;


    @Test
    public void testGetProcessDefinition() {
        ProcessDefinition definition = processDefinitionMapper.findLatestByKey("updateDocument");
        System.out.println(definition.getResourceName());
        LambdaQueryWrapper<ProcessDefinition> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        System.out.println(JsonUtil.toJson(processDefinitionMapper.selectPage(PageDTO.of(1, 10), lambdaQueryWrapper)));
    }

    @Test
    public void testGetActivityTasks() {
        LambdaQueryChainWrapper<ActivityTask> wrapper = new LambdaQueryChainWrapper<>(activityTaskMapper);
        System.out.println(wrapper.eq(ActivityTask::getProcInstId, 1L).one());
    }
}