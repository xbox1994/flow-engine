package com.wty.flowengine.converter.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.wty.flowengine.rest.demo.test.model.FlowElement;
import com.wty.flowengine.rest.demo.test.model.ServiceTask;
import com.wty.flowengine.converter.constants.Field;
import com.wty.flowengine.converter.exception.InvalidFlowElementException;
import com.wty.flowengine.converter.util.FlowElementUtil;
import com.wty.flowengine.converter.util.JsonNodeUtil;
import org.assertj.core.util.Strings;


public class ServiceTaskFactory implements FlowElementFactory {
    public static final ServiceTaskFactory instance = new ServiceTaskFactory();

    private ServiceTaskFactory() {

    }

    @Override
    public FlowElement create(JsonNode elementNode) {
        ServiceTask serviceTask = new ServiceTask();
        FlowElementUtil.readBaseProperties(serviceTask, elementNode);
        JsonNode configNode = elementNode.get(Field.CONFIG);
        if(configNode == null) {
            throw  new InvalidFlowElementException(serviceTask, "'config' required");
        }
        readConfig(serviceTask, configNode);
        return serviceTask;
    }

    private void readConfig(ServiceTask serviceTask, JsonNode configNode) {
        String implementation = JsonNodeUtil.getString(configNode, Field.SERVICE_TASK_IMPLEMENTATION);
        if(Strings.isNullOrEmpty(implementation)) {
            throw new InvalidFlowElementException(serviceTask, "'implementation' required.");
        }
        serviceTask.setImplementation(implementation);
        serviceTask.setBeanName(JsonNodeUtil.getString(configNode, "beanName"));
        serviceTask.setScript(JsonNodeUtil.getString(configNode, "script"));
        serviceTask.setClassName(JsonNodeUtil.getString(configNode, "className"));
        serviceTask.setManual(JsonNodeUtil.getBoolean(configNode, "manual"));
    }
}
