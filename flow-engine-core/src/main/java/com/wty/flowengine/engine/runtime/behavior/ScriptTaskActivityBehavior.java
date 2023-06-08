package com.wty.flowengine.engine.runtime.behavior;

import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.runtime.util.CommandContextUtil;

public class ScriptTaskActivityBehavior extends FlowNodeActivityBehavior implements ActivityBehavior {
    private final String script;

    public ScriptTaskActivityBehavior(String script) {
        this.script = script;
    }

    @Override
    public void execute(Execution execution) {
        try {
            CommandContextUtil.getProcessEngineConfiguration()
                    .getGroovyScriptExecutor()
                    .execute(script, execution.getVariables());
            // 如果执行过程中抛出异常，那么流程实例会被终止，抛出异常的流程节点会被标记为失败
        }catch (Exception e) {
            CommandContextUtil.getProcessEngineConfiguration()
                    .getProcessEventLogRepository()
                    .recordActivityFailure(execution, e);
            throw e;
        }
        super.leave(execution);
    }
}
