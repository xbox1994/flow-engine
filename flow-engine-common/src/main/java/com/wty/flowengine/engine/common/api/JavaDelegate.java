package com.wty.flowengine.engine.common.api;

/**
 * ServiceTask的实现方式是 "JavaClass" 时，指定的类需要实现该接口
 */
public interface JavaDelegate {
    void execute(VariableScope variableScope);
}
