package com.wty.flowengine.engine.runtime.util;

import com.wty.flow.model.SequenceFlow;
import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.common.script.GroovyScriptExecutor;
import com.wty.flowengine.engine.domain.Execution;
import org.assertj.core.util.Strings;

public class ConditionUtil {

    public static boolean hasTrueCondition(SequenceFlow outgoingFlow, Execution execution) {
        String condition = outgoingFlow.getCondition();
        if (Strings.isNullOrEmpty(condition)) {
            return true;
        }
        // 使用Groovy引擎计算
        ProcessEngineConfiguration configuration = CommandContextUtil.getProcessEngineConfiguration();
        GroovyScriptExecutor groovyScriptExecutor = configuration.getGroovyScriptExecutor();
        Object value = groovyScriptExecutor.execute(condition, execution.getVariables());
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        throw new FlowEngineException("Expression value isn't boolean.");

    }
}
