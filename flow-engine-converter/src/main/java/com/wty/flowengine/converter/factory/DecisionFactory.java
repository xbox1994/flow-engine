package com.wty.flowengine.converter.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.wty.flow.model.Decision;
import com.wty.flow.model.FlowElement;
import com.wty.flowengine.converter.constants.Field;
import com.wty.flowengine.converter.util.FlowElementUtil;
import com.wty.flowengine.converter.util.JsonNodeUtil;

public class DecisionFactory implements FlowElementFactory{

    public static final DecisionFactory instance = new DecisionFactory();

    private DecisionFactory() {

    }

    @Override
    public FlowElement create(JsonNode elementNode) {
        Decision decision = new Decision();
        FlowElementUtil.readBaseProperties(decision, elementNode);
        JsonNode configNode = elementNode.get(Field.CONFIG);
        if(configNode != null) {
            readConfig(decision, configNode);
        }
        return decision;
    }

    private void readConfig(Decision decision, JsonNode configNode) {
        String defaultFlow = JsonNodeUtil.getString(configNode, Field.ACTIVITY_DEFAULT_SEQUENCE_FLOW);
        decision.setDefaultFlowId(defaultFlow);
    }
}
