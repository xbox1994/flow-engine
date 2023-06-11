package com.wty.flowengine.rest.demo.test.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Process extends FlowElement {

    private List<FlowElement> flowElements = new ArrayList<>();

    private Map<String, FlowElement> flowElementMap = new HashMap<>();

    private FlowElement start;

    public List<FlowElement> getFlowElements() {
        return flowElements;
    }

    public void setFlowElements(List<FlowElement> flowElements) {
        this.flowElements = flowElements;
    }

    public Map<String, FlowElement> getFlowElementMap() {
        return flowElementMap;
    }

    public void setFlowElementMap(Map<String, FlowElement> flowElementMap) {
        this.flowElementMap = flowElementMap;
    }

    public FlowElement getStart() {
        return start;
    }

    public void setStart(FlowElement start) {
        this.start = start;
    }

    public FlowElement getFlowElement(String id) {
        return flowElementMap != null ? flowElementMap.get(id) : null;
    }
}
