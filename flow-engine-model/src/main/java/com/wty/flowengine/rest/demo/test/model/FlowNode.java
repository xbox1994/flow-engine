package com.wty.flowengine.rest.demo.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class FlowNode extends FlowElement {
    /**
     * 进入的连线集合
     */
    protected List<SequenceFlow> incomingFlows = new ArrayList<>();

    /**
     * 出去的连线集合
     */
    protected List<SequenceFlow> outgoingFlows = new ArrayList<>();


    @JsonIgnore
    protected Object behavior;

    public List<SequenceFlow> getIncomingFlows() {
        return incomingFlows;
    }

    public void setIncomingFlows(List<SequenceFlow> incomingFlows) {
        this.incomingFlows = incomingFlows;
    }

    public List<SequenceFlow> getOutgoingFlows() {
        return outgoingFlows;
    }

    public void setOutgoingFlows(List<SequenceFlow> outgoingFlows) {
        this.outgoingFlows = outgoingFlows;
    }

    public Object getBehavior() {
        return behavior;
    }

    public void setBehavior(Object behavior) {
        this.behavior = behavior;
    }
}
