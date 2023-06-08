package com.wty.flowengine.converter.exception;

import com.wty.flow.model.FlowElement;

public class InvalidProcessException extends InvalidFlowElementException{

    public InvalidProcessException(String message) {
        super(message);
    }

    public InvalidProcessException(FlowElement flowElement, String message) {
        super(flowElement, message);
    }
}
