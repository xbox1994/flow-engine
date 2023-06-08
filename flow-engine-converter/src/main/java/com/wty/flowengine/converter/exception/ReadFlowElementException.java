package com.wty.flowengine.converter.exception;

import com.wty.flow.model.FlowElement;

public class ReadFlowElementException extends InvalidFlowElementException {


    public ReadFlowElementException(String message) {
        super(message);
    }

    public ReadFlowElementException(FlowElement flowElement, String message) {
        super(flowElement, message);
    }

    public ReadFlowElementException(FlowElement flowElement, String message, Throwable cause) {
        super(flowElement, message, cause);
    }

}
