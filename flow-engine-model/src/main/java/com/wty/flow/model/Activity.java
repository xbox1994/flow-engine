package com.wty.flow.model;

public abstract class Activity extends FlowNode {
    protected String defaultFlowId;

    protected SequenceFlow defaultFlow;

    protected boolean manual;

    public String getDefaultFlowId() {
        return defaultFlowId;
    }

    public void setDefaultFlowId(String defaultFlowId) {
        this.defaultFlowId = defaultFlowId;
    }

    public SequenceFlow getDefaultFlow() {
        return defaultFlow;
    }

    public void setDefaultFlow(SequenceFlow defaultFlow) {
        this.defaultFlow = defaultFlow;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }
}
