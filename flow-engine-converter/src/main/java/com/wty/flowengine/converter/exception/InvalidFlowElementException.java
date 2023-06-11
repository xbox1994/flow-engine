package com.wty.flowengine.converter.exception;

import com.wty.flowengine.rest.demo.test.model.FlowElement;

public class InvalidFlowElementException extends RuntimeException {
    private FlowElement flowElement;

    public InvalidFlowElementException(String message) {
        super(message);
    }

    public InvalidFlowElementException(FlowElement flowElement, String message) {
        super(message);
        this.flowElement = flowElement;
    }

    public InvalidFlowElementException(FlowElement flowElement, String message, Throwable cause) {
        super(message, cause);
        this.flowElement = flowElement;
    }

    @Override
    public String toString() {
        String str = super.toString();
        String elementInfo = "target: " + (flowElement == null ? "null" : flowElement.toString());
        return str + ", " + elementInfo;
    }
}
