package com.wty.flowengine.converter.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.wty.flow.model.FlowElement;
import com.wty.flow.model.SequenceFlow;
import com.wty.flowengine.converter.constants.Field;
import com.wty.flowengine.converter.util.FlowElementUtil;
import com.wty.flowengine.converter.util.JsonNodeUtil;


public class SequenceFlowFactory implements FlowElementFactory {
    public static final SequenceFlowFactory instance = new SequenceFlowFactory();

    private SequenceFlowFactory() {

    }

    @Override
    public FlowElement create(JsonNode elementNode) {
        SequenceFlow sequenceFlow = new SequenceFlow();
        FlowElementUtil.readBaseProperties(sequenceFlow, elementNode);
        readProperties(sequenceFlow, elementNode);
        return sequenceFlow;
    }

    private void readProperties(SequenceFlow sequenceFlow, JsonNode elementNode) {
        String sourceId = JsonNodeUtil.getString(elementNode, Field.SEQUENCE_FLOW_SOURCE_ID);
        String targetId = JsonNodeUtil.getString(elementNode, Field.SEQUENCE_FLOW_TARGET_ID);
        sequenceFlow.setSourceId(sourceId);
        sequenceFlow.setTargetId(targetId);
        JsonNode configNode = elementNode.get(Field.CONFIG);
        if (configNode != null) {
            readConfig(sequenceFlow, configNode);
        }
    }

    private void readConfig(SequenceFlow sequenceFlow, JsonNode configNode) {
        String condition = JsonNodeUtil.getString(configNode, Field.SEQUENCE_FLOW_CONDITION);
        sequenceFlow.setCondition(condition);
    }
}
