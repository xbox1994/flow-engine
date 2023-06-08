package com.wty.flowengine.engine.common.api;

import java.util.Map;

/**
 * 如果VariableScope存在层次结果，所有的变量都保存在顶层对象上，即<b>不支持局部变量</b>
 */
public interface VariableScope {
    Object getVariable(String name);

    void setVariable(String name, Object value);

    Map<String, Object> getVariables();

    void removeVariable(String name);

    void removeVariables();

    boolean hasVariable(String name);

    void setVariables(Map<String, Object> variables);
}
