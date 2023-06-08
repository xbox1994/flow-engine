package com.wty.flowengine.sdk.exec.util;

import com.googlecode.aviator.AviatorEvaluator;
import com.wty.flow.model.SequenceFlow;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.sdk.exec.vo.Token;
import org.assertj.core.util.Strings;

public class ConditionUtil {

    public static boolean hasTrueCondition(SequenceFlow sequenceFlow, Token token) {
        String condition = sequenceFlow.getCondition();
        if(Strings.isNullOrEmpty(condition)) {
            return true;
        }
        Object value = AviatorEvaluator.execute(condition, token.getVariables());
        if(value instanceof Boolean) {
            return (Boolean) value;
        }
        throw new FlowEngineException("Expression value isn't boolean.");
    }
}
