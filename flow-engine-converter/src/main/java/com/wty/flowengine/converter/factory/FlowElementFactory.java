package com.wty.flowengine.converter.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.wty.flowengine.rest.demo.test.model.FlowElement;
import com.wty.flowengine.converter.constants.FlowElementType;
import com.wty.flowengine.converter.util.FlowElementUtil;

public interface FlowElementFactory {
    FlowElementFactory instance = new FlowElementFactoryImpl();

    FlowElement create(JsonNode elementNode);

    class FlowElementFactoryImpl implements FlowElementFactory {

        public static FlowElementFactoryImpl instance = new FlowElementFactoryImpl();

        private FlowElementFactoryImpl() {

        }

        @Override
        public FlowElement create(JsonNode elementNode) {
            FlowElementType elementType = FlowElementUtil.getElementType(elementNode);
            switch (elementType) {
                case PROCESS:
                    return ProcessFactory.instance.create(elementNode);
                case START:
                    return StartFactory.instance.create(elementNode);
                case END:
                    return EndFactory.instance.create(elementNode);
                case SERVICE_TASK:
                    return ServiceTaskFactory.instance.create(elementNode);
                case SEQUENCE_FLOW:
                    return SequenceFlowFactory.instance.create(elementNode);
                case DECISION:
                    return DecisionFactory.instance.create(elementNode);
                case USER_TASK:
                    return UserTaskFactory.instance.create(elementNode);
                case CALL_ACTIVITY:
                    return CallActivityFactory.instance.create(elementNode);
                default:
                    throw new RuntimeException("Not implemented, type: " + elementType);
            }
        }
    }
}


