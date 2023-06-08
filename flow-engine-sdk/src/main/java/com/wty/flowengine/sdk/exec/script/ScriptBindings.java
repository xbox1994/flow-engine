package com.wty.flowengine.sdk.exec.script;

import com.wty.flowengine.engine.common.api.VariableScope;

import javax.annotation.Nonnull;
import javax.script.Bindings;
import java.util.*;

public class ScriptBindings implements Bindings {

    private final VariableScope variableScope;

    public ScriptBindings(VariableScope variableScope) {
        this.variableScope = variableScope;
    }

    @Override
    public Object put(String name, Object value) {
        Object oldValue = variableScope.getVariable(name);
        variableScope.setVariable(name, value);
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends String, ?> toMerge) {
        toMerge.forEach(this::put);
    }

    @Override
    public void clear() {
        this.variableScope.removeVariables();
    }

    @Override
    public Set<String> keySet() {
        Map<String, Object> variables = this.variableScope.getVariables();
        return variables != null ? variables.keySet() : new HashSet<>();
    }

    @Override
    public Collection<Object> values() {
        Map<String, Object> variables = this.variableScope.getVariables();
        if (variables != null) {
            return variables.values();
        }
        return new ArrayList<>();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return getVariables().entrySet();
    }

    @Override
    public int size() {
        return getVariables().size();
    }

    @Override
    public boolean isEmpty() {
        return getVariables().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.variableScope.hasVariable((String) key);
    }

    @Override
    public boolean containsValue(Object value) {
        return getVariables().containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return variableScope.getVariable((String) key);
    }

    @Override
    public Object remove(Object key) {
        Object oldValue = variableScope.getVariable((String) key);
        variableScope.removeVariable((String) key);
        return oldValue;
    }

    @Nonnull
    private Map<String, Object> getVariables() {
        Map<String, Object> variables = this.variableScope.getVariables();
        return variables != null ? variables : new HashMap<>();
    }
}
