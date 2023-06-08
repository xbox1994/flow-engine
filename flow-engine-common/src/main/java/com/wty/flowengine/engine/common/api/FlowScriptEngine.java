package com.wty.flowengine.engine.common.api;

public interface FlowScriptEngine {
    void execute(String script, VariableScope variableScope);
}
