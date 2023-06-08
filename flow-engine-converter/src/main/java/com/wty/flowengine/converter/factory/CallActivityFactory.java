package com.wty.flowengine.converter.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.wty.flow.model.CallActivity;
import com.wty.flow.model.FlowElement;
import com.wty.flowengine.converter.constants.Field;
import com.wty.flowengine.converter.exception.InvalidFlowElementException;
import com.wty.flowengine.converter.util.FlowElementUtil;
import com.wty.flowengine.converter.util.JsonNodeUtil;
import org.assertj.core.util.Strings;


public class CallActivityFactory implements FlowElementFactory {
    public static final CallActivityFactory instance = new CallActivityFactory();

    private CallActivityFactory() {

    }

    @Override
    public FlowElement create(JsonNode elementNode) {
        CallActivity callActivity = new CallActivity();
        FlowElementUtil.readBaseProperties(callActivity, elementNode);
        JsonNode configNode = elementNode.get(Field.CONFIG);
        if (configNode == null) {
            throw new InvalidFlowElementException(callActivity, "'config' required");
        }
        readConfig(callActivity, configNode);
        return callActivity;
    }

    private void readConfig(CallActivity callActivity, JsonNode configNode) {
        String processId = JsonNodeUtil.getString(configNode, Field.CALL_ACTIVITY_PROCESS_ID);
        if (Strings.isNullOrEmpty(processId)) {
            throw new InvalidFlowElementException(callActivity, "'processId' required.");
        }
        callActivity.setProcessId(processId);
    }
}
