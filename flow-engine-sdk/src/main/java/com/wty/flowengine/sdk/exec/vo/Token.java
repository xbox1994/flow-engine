package com.wty.flowengine.sdk.exec.vo;

import com.wty.flowengine.rest.demo.test.model.FlowElement;
import com.wty.flowengine.rest.demo.test.model.Process;
import com.wty.flowengine.engine.common.api.VariableScope;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Token implements VariableScope {

    private Process process;

    private FlowElement currentFlowElement;

    private Token parent;

    private Map<String, Object> variables = new HashMap<>();

    private Token(Process process, FlowElement currentFlowElement, Token parent) {
        this.process = process;
        this.currentFlowElement = currentFlowElement;
        this.parent = parent;
    }

    public Token createChildToken(FlowElement current) {
        return new Token(process, current, this);
    }

    public static Token create(Process process, FlowElement current, Map<String, Object> variables) {
        Token token = new Token(process, current, null);
        if (variables != null) token.variables.putAll(variables);
        return token;
    }

    @Override
    public Object getVariable(String name) {
        return getVariableInternal().get(name);
    }

    @Override
    public void setVariable(String name, Object value) {
        getVariableInternal().put(name, value);
    }

    @Override
    public void removeVariable(String name) {
        getVariableInternal().remove(name);
    }

    @Override
    public void removeVariables() {
        getVariableInternal().clear();
    }

    @Override
    public boolean hasVariable(String name) {
        return getVariableInternal().containsKey(name);
    }

    private Map<String, Object> getVariableInternal() {
        if (parent != null) {
            return parent.getVariableInternal();
        } else {
            return variables;
        }
    }
}
