package com.wty.flowengine.rest.demo.test;

import com.google.common.collect.Maps;
import com.wty.flowengine.api.request.StartFlowDto;
import com.wty.flowengine.api.request.TriggerDto;
import com.wty.flowengine.api.response.BaseResponse;
import com.wty.flowengine.rest.demo.flow.FlowApplication;
import com.wty.flowengine.rest.demo.flow.client.FlowClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * description
 * @author Tianyi.Wang5
 * @since 2023/06/09 14:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowApplication.class)
@ActiveProfiles("dev")
@Slf4j
public class FlowTest {
    public static final String NODE_MANUAL_TASK_3 = "task3";
    @Autowired
    private FlowClient flowClient;
    public static final String TEST_PROCESS = "testProcess";
    public static final String BIZ_ID = "1";

    @Test
    public void create() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("process/flow-test.json").getFile());
        String s = FileUtils.readFileToString(file);
        flowClient.processUpload(s);
    }

    @Test
    public void start() {
        Map<String, Object> variables = Maps.newHashMap();
        // 业务流中的判断条件
        variables.put("pullMode", 2);
        variables.put("supplyType", 3);
        // 核心数据
        variables.put("outwardCreateMessage", "testsss");
        // 启动流程
        flowClient.startByKey(StartFlowDto.builder().bizId(BIZ_ID)// 业务id
            .defKey(TEST_PROCESS)// 流程名称，固定
            .variables(variables).build());
    }

    @Test
    public void complete() {
        // 避免前序流程没有启动，本开关开启后没有查到任务的情况
        BaseResponse<Long> taskResponse = flowClient.getTaskId(TEST_PROCESS, BIZ_ID, NODE_MANUAL_TASK_3);
        if (taskResponse.getCode() == 0 && taskResponse.getBody() != null) {
            Map<String, Object> variables = Maps.newHashMap();
            TriggerDto triggerDto = new TriggerDto();
            triggerDto.setBizId(BIZ_ID);
            triggerDto.setDefKey(TEST_PROCESS);
            triggerDto.setVariables(variables);
            variables.put("message", "xxxxxxxxxxxxxxxxxxxxxx");
            triggerDto.setNodeId(NODE_MANUAL_TASK_3);
            // 非自动节点专用，执行下一步
            flowClient.complete(triggerDto);
        }
    }
}
