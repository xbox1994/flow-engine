package com.wty.flowengine.converter.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.wty.flow.model.FlowElement;
import com.wty.flow.model.Start;
import com.wty.flowengine.converter.util.FlowElementUtil;


public class StartFactory implements FlowElementFactory {
    public static final StartFactory instance = new StartFactory();

    private StartFactory() {

    }

    @Override
    public FlowElement create(JsonNode elementNode) {
        Start start = new Start();
        FlowElementUtil.readBaseProperties(start, elementNode);
        return start;
    }
}
