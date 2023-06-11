package com.wty.flowengine.converter.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.wty.flowengine.rest.demo.test.model.End;
import com.wty.flowengine.rest.demo.test.model.FlowElement;
import com.wty.flowengine.converter.util.FlowElementUtil;


public class EndFactory implements FlowElementFactory {
    public static final EndFactory instance = new EndFactory();

    private EndFactory() {

    }

    @Override
    public FlowElement create(JsonNode elementNode) {
        End end = new End();
        FlowElementUtil.readBaseProperties(end, elementNode);
        return end;
    }
}
