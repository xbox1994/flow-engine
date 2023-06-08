package com.wty.flowengine.sdk.exec.script;

import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.common.api.FlowScriptEngine;
import com.wty.flowengine.engine.common.api.VariableScope;
import com.wty.flowengine.engine.common.script.GroovyScriptExecutor;

public class GroovyScriptEngine implements FlowScriptEngine {

    private final GroovyScriptExecutor groovyScriptExecutor;

    public GroovyScriptEngine() {
        groovyScriptExecutor = new GroovyScriptExecutor();
    }

    @Override
    public void execute(String script, VariableScope variableScope) {
        try {
            groovyScriptExecutor.execute(script, new ScriptBindings(variableScope));
        } catch (Exception e) {
            throw new FlowEngineException("Execute script failed.", e);
        }
    }


}
