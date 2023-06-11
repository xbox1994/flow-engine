package com.wty.flowengine.engine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.wty.flowengine.engine.common.utils.JsonUtil;
import com.wty.flowengine.engine.domain.ActivityTask;
import com.wty.flowengine.engine.domain.Deployment;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.domain.ProcessEventLog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {TestConfiguration.class})
public class ProcessEngineTest {

    @Resource
    private ProcessEngine processEngine;

    @Test
    @DisplayName("通过Key发起流程")
    public void testBuildProcessEngine() throws InterruptedException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("role", "admin");
        Map<String, Object> data = new HashMap<>();
        data.put("name", "测试文件.pdf");
        Map<String, Object> session = new HashMap<>();
        session.put("employeeNo", "100001");
        variables.put("data", data);
        variables.put("session", session);
        Execution processInstance = processEngine.getRuntimeService()
                .startByKey("updateDocument", variables);
        TimeUnit.SECONDS.sleep(5);
        System.out.println("processInstance: " + JsonUtil.toJson(processInstance)); // 查看流程实例状态
        // 数据库中应该没有这个 execution 的数据了
        List<ProcessEventLog> processEventLogs = processEngine.getProcessEngineConfiguration()
                .getProcessEventLogRepository().listByExecutionId(processInstance.getId());
        if (processEventLogs != null && !processEventLogs.isEmpty()) {
            for (ProcessEventLog processEventLog : processEventLogs) {
                System.out.println(JsonUtil.toJson(processEventLog));
            }
        }
    }


    @Test
    @DisplayName("部署流程定义")
    public void testDeployProcessDefinition() throws IOException {
        Deployment deployment = deployFromFile("processes/updateDoc.json");
        System.out.println(deployment);
    }

    public Deployment deployFromFile(String path) throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
        JsonNode jsonNode = new ObjectMapper().readTree(inputStream);
        return processEngine.getRepositoryService().deploy(jsonNode);
    }


    @Test
    @DisplayName("删除流程定义-ByKey")
    public void testDeleteDeployment() {
        processEngine.getRepositoryService().deleteProcessDefinition("updateDocument");
    }

    @Test
    @DisplayName("删除部署-ByDeploymentId")
    public void testDeleteDeploymentById() throws IOException {
        Deployment deployment = deployFromFile("processes/testUserTask.json");
        System.out.println(deployment);
        processEngine.getRepositoryService().deleteDeployment(deployment.getId());
    }

    @Test
    @DisplayName("执行节点异常和异常恢复")
    public void testRunInTransaction() throws InterruptedException {
//        Deployment deployment = deployFromFile("processes/testNodeTx.json");

        Map<String, Object> context = ImmutableMap.of("ready", false);
        Execution instance = processEngine.getRuntimeService().startByKey("testNodeTx", context);
        System.out.println(instance);
        TimeUnit.SECONDS.sleep(2); // 等待流程执行完毕
        List<ProcessEventLog> processEventLogs = processEngine.getProcessEngineConfiguration()
                .getProcessEventLogRepository().listByExecutionId(instance.getId());

        Long failedExecutionId = null;
        for (ProcessEventLog processEventLog : processEventLogs) {
            System.out.println(JsonUtil.toJson(processEventLog));
            if (processEventLog.getEvent().equals(ProcessEventLog.Event.EXECUTION_FAILED)) {
                failedExecutionId = processEventLog.getExecutionId();
            }
        }

        if (failedExecutionId != null) {
            // 从异常节点重试
            // 考虑并发情况下，如何保证只有一个线程可以执行这个节点
            processEngine.getRuntimeService().continueExecution(failedExecutionId, ImmutableMap.of("ready", true));
        }

        TimeUnit.SECONDS.sleep(2);

        // 查看流程实例的状态和流程日志

        showProcessLog(instance.getId());
    }

    @Test
    @DisplayName("人工节点")
    public void testManualTask() throws InterruptedException {
        // deployFromFile("processes/testManualTask.json");
        Map<String, Object> context = ImmutableMap.of("ready", true);
        String bizId = "" + System.currentTimeMillis();
        Execution instance = processEngine.getRuntimeService().startByKey("testManualTask", bizId, context);
        System.out.println(instance);
        TimeUnit.SECONDS.sleep(2); // 等待流程执行完毕
        List<ProcessEventLog> processEventLogs = processEngine.getProcessEngineConfiguration()
                .getProcessEventLogRepository().listByExecutionId(instance.getId());

        for (ProcessEventLog processEventLog : processEventLogs) {
            System.out.println(JsonUtil.toJson(processEventLog));
        }

        System.out.println("查询人工触发节点任务");
        List<ActivityTask> activityTasks = processEngine.getTaskService().listActivityTasks(instance.getId());
        assertThat(activityTasks.size()).isEqualTo(1);
        assertThat(activityTasks.get(0).getActivityName()).isEqualTo("人工触发");

        System.out.println("人工触发节点任务：" + JsonUtil.toJson(activityTasks.get(0)));
        // 触发完成任务
        // processEngine.getTaskService().complete(activityTasks.get(0).getId(), ImmutableMap.of("ready", true));
        processEngine.getTaskService().complete("testManualTask", bizId, "task1", ImmutableMap.of("ready", true));

        TimeUnit.SECONDS.sleep(2);

        System.out.println("查看流程实例的状态和流程日志");
        showProcessLog(instance.getId());


    }

    @Test
    @DisplayName("测试UserTask")
    public void testUserTask() throws InterruptedException {
        // 启动流程实例
        Execution instance = processEngine.getRuntimeService().startByKey("testUserTask", ImmutableMap.of());
        System.out.println("启动流程实例成功：" + instance);
        Thread.sleep(1000);
        // 查看人工节点任务
        List<ActivityTask> activityTasks = processEngine.getTaskService().listActivityTasks(instance.getId());
        assertThat(activityTasks.size()).isEqualTo(1);
        System.out.println("执行人工节点任务：" + activityTasks.get(0));
        processEngine.getTaskService().complete(activityTasks.get(0).getId(), null);
        Thread.sleep(1000);
        // 查看流程实例的状态和流程日志
        showProcessLog(instance.getId());

    }

    private void showProcessLog(Long executionId) {
        processEngine.getProcessEngineConfiguration().getProcessEventLogRepository()
                .listByExecutionId(executionId).forEach(System.out::println);
    }

    @Test
    @DisplayName("测试调用节点CallActivity")
    public void testCallActivity() throws IOException, InterruptedException {
        // 发起流程实例 todo fixme
        Execution instance = processEngine.getRuntimeService()
                .startByKey("testCallActivity", ImmutableMap.of("ready", true));
        System.out.println("启动流程实例成功：" + instance.getId());
        Thread.sleep(2000);
        // 查看流程实例的状态和流程日志
        showProcessLog(instance.getId());
    }

    @Test
    public void testFlow() throws IOException, InterruptedException {
//        Deployment deployment = deployFromFile("processes/flow-test.json");
//        System.out.println(deployment.getId());

        Map<String, Object> variables = Maps.newHashMap();
        // 业务流中的判断条件
        variables.put("pullMode", 2);
        variables.put("supplyType", 3);
        // 核心数据
        variables.put("outwardCreateMessage", "testsss");
        // 启动流程
        String bizId = String.valueOf(System.currentTimeMillis());
        Execution execution = processEngine.getRuntimeService().startByKey("testProcess", bizId, variables);
        System.out.println("启动流程实例成功：" + JsonUtil.toJson(execution));
        Thread.sleep(10000);
        System.out.println("查看流程实例的状态和流程日志");
    }

}