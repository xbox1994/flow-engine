package com.wty.flowengine.sdk.exec.behavior;

import com.wty.flowengine.sdk.exec.util.CommandContextUtil;
import com.wty.flowengine.sdk.exec.vo.Token;

public class ScriptTaskActivityBehavior extends FlowNodeActivityBehavior implements ActivityBehavior{
    private final String script;

    public ScriptTaskActivityBehavior(String script) {
        this.script = script;
    }

    @Override
    public void execute(Token token) {
        CommandContextUtil.getProcessExecutorConfiguration().getScriptEngine().execute(script, token);
        super.leave(token);
    }
}
