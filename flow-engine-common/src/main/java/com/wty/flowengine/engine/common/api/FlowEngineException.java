package com.wty.flowengine.engine.common.api;

public class FlowEngineException extends RuntimeException{

    public FlowEngineException(String message) {
        super(message);
    }

    public FlowEngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlowEngineException(Throwable cause) {
        super(cause);
    }
}
