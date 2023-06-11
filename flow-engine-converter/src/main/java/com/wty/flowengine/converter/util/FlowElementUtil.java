package com.wty.flowengine.converter.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import com.wty.flowengine.rest.demo.test.model.*;
import com.wty.flowengine.converter.constants.Field;
import com.wty.flowengine.converter.constants.FlowElementType;
import com.wty.flowengine.converter.exception.InvalidFlowElementException;
import com.wty.flowengine.converter.exception.ReadFlowElementException;
import com.wty.flowengine.rest.demo.test.model.Process;

import java.util.HashMap;
import java.util.Map;

public class FlowElementUtil {

    private static final Map<Class<? extends FlowElement>, FlowElementType> typeMap = new HashMap<>();

    static {
        typeMap.put(Process.class, FlowElementType.PROCESS);
        typeMap.put(Start.class, FlowElementType.START);
        typeMap.put(End.class, FlowElementType.END);
        typeMap.put(ServiceTask.class, FlowElementType.SERVICE_TASK);
        typeMap.put(SequenceFlow.class, FlowElementType.SEQUENCE_FLOW);
        typeMap.put(Decision.class, FlowElementType.DECISION);
        typeMap.put(UserTask.class, FlowElementType.USER_TASK);
        typeMap.put(CallActivity.class, FlowElementType.CALL_ACTIVITY);
    }

    public static FlowElementType get(FlowElement flowElement) {
        FlowElementType flowElementType = typeMap.get(flowElement.getClass());
        if (flowElementType == null) {
            throw new RuntimeException("Not implemented");
        }
        return flowElementType;
    }

    public static FlowElementType getElementType(JsonNode jsonNode) {
        JsonNode type = jsonNode.get("type");
        if (type == null) {
            throw new InvalidFlowElementException("Type required");
        }
        return FlowElementType.valueOf(type.asText());
    }

    public static void readBaseProperties(FlowElement flowElement, JsonNode jsonNode) {
        String id = JsonNodeUtil.getString(jsonNode, Field.ID);
        if (Strings.isNullOrEmpty(id)) {
            throw new InvalidFlowElementException("Id required");
        }
        flowElement.setId(id);

        String type = JsonNodeUtil.getString(jsonNode, Field.TYPE);
        if (Strings.isNullOrEmpty(type)) {
            throw new InvalidFlowElementException(flowElement, "Type required");
        }
        if (FlowElementType.valueOf(type) != FlowElementUtil.get(flowElement)) {
            throw new ReadFlowElementException(flowElement, "Wrong type");
        }

        flowElement.setName(JsonNodeUtil.getString(jsonNode, Field.NAME));

        flowElement.setDescription(JsonNodeUtil.getString(jsonNode, Field.DESCRIPTION));
    }
}
