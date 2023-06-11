package com.wty.flowengine.converter.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import com.wty.flowengine.rest.demo.test.model.*;
import com.wty.flowengine.converter.constants.Field;
import com.wty.flowengine.converter.exception.InvalidFlowElementException;
import com.wty.flowengine.converter.exception.InvalidProcessException;
import com.wty.flowengine.converter.util.FlowElementUtil;
import com.wty.flowengine.rest.demo.test.model.Process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessFactory implements FlowElementFactory {

    public static final ProcessFactory instance = new ProcessFactory();

    private ProcessFactory() {
    }

    public Process createProcess(JsonNode processNode) {
        Process process = new Process();
        FlowElementUtil.readBaseProperties(process, processNode);
        readProcessProperties(process, processNode);
        postProcess(process);
        return process;
    }

    private void postProcess(Process process) {
        List<FlowElement> flowElements = process.getFlowElements();
        Map<String, FlowElement> flowElementMap = new HashMap<>(flowElements.size());
        for (FlowElement flowElement : flowElements) {
            flowElementMap.put(flowElement.getId(), flowElement);
        }
        process.setFlowElementMap(flowElementMap);

        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof SequenceFlow) {
                processSequenceFlow((SequenceFlow) flowElement, process);
            } else if (flowElement instanceof FlowNode) {
                if (flowElement instanceof Start) {
                    handleStart(process, flowElement);
                }
                if (flowElement instanceof Activity) {
                    handleActivity(process, (Activity) flowElement);
                }
            } else {
                throw new InvalidProcessException("Unknown flowElement: " + flowElement);
            }
            // todo 可能存在无法关联到的元素
        }
    }

    private void handleStart(Process process, FlowElement flowElement) {
        if (process.getStart() == null) {
            process.setStart(flowElement);
        } else {
            throw new InvalidProcessException("More than one start flowElement found");
        }
    }

    private void handleActivity(Process process, Activity activity) {
        String defaultFlowId = activity.getDefaultFlowId();
        if (Strings.isNullOrEmpty(defaultFlowId)) {
            return;
        }
        FlowElement flowElement = process.getFlowElement(defaultFlowId);
        if (flowElement == null) {
            throw new InvalidProcessException("Unknown FlowElement: " + defaultFlowId);
        }
        if (!(flowElement instanceof SequenceFlow)) {
            throw new InvalidProcessException("Activity's default flow must be SequenceFlow");
        }
        activity.setDefaultFlow((SequenceFlow) flowElement);
    }

    private void processSequenceFlow(SequenceFlow sequenceFlow, Process process) {
        String sourceId = sequenceFlow.getSourceId();
        if (Strings.isNullOrEmpty(sourceId)) {
            throw new InvalidFlowElementException(sequenceFlow, "SequenceFlow's sourceId is required");
        }
        FlowElement sourceElement = process.getFlowElement(sourceId);
        if (sourceElement == null) {
            throw new InvalidProcessException("Unknown FlowElement: " + sourceId);
        }
        if (!(sourceElement instanceof FlowNode)) {
            throw new InvalidProcessException("SequenceFlow's source must be FlowNode");
        }
        FlowNode sourceNode = (FlowNode) sourceElement;
        sequenceFlow.setSource(sourceElement);
        sourceNode.getOutgoingFlows().add(sequenceFlow);

        String targetId = sequenceFlow.getTargetId();
        if (Strings.isNullOrEmpty(targetId)) {
            throw new InvalidFlowElementException(sequenceFlow, "SequenceFlow's targetId is required");
        }
        FlowElement targetElement = process.getFlowElement(targetId);
        if (targetElement == null) {
            throw new InvalidProcessException("Unknown FlowElement: " + targetId);
        }
        if (!(targetElement instanceof FlowNode)) {
            throw new InvalidProcessException("SequenceFlow's target must be FlowNode");
        }
        FlowNode targetNode = (FlowNode) targetElement;
        sequenceFlow.setTarget(targetNode);
        targetNode.getIncomingFlows().add(sequenceFlow);
    }

    private void readProcessProperties(Process process, JsonNode processNode) {
        List<FlowElement> flowElements = new ArrayList<>();
        JsonNode elementsNode = processNode.get(Field.PROCESS_ELEMENTS);
        if (elementsNode == null) {
            return;
        }
        for (JsonNode elementNode : elementsNode) {
            flowElements.add(FlowElementFactory.instance.create(elementNode));
        }
        process.setFlowElements(flowElements);
    }


    @Override
    public FlowElement create(JsonNode elementNode) {
        return createProcess(elementNode);
    }
}
