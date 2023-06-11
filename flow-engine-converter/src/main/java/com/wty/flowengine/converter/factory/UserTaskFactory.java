package com.wty.flowengine.converter.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.wty.flowengine.rest.demo.test.model.FlowElement;
import com.wty.flowengine.rest.demo.test.model.ServiceTask;
import com.wty.flowengine.rest.demo.test.model.UserTask;
import com.wty.flowengine.converter.util.FlowElementUtil;


public class UserTaskFactory implements FlowElementFactory {
    public static final UserTaskFactory instance = new UserTaskFactory();

    private UserTaskFactory() {

    }

    @Override
    public FlowElement create(JsonNode elementNode) {
        UserTask userTask = new UserTask();
        FlowElementUtil.readBaseProperties(userTask, elementNode);
        return userTask;
    }

    // todo 如果需要增加其他节点信息，可以在这里配置
    private void readConfig(ServiceTask serviceTask, JsonNode configNode) {

    }
}
