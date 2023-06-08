package com.wty.flowengine.engine.runtime.behavior;

import com.wty.flowengine.engine.common.api.JavaDelegate;
import com.wty.flowengine.engine.domain.Execution;

public class JavaDelegateActivityBehavior extends FlowNodeActivityBehavior implements ActivityBehavior {

    private final JavaDelegate javaDelegate;

    public JavaDelegateActivityBehavior(JavaDelegate javaDelegate) {
        this.javaDelegate = javaDelegate;
    }

    @Override
    public void execute(Execution execution) {
        javaDelegate.execute(execution);
        super.leave(execution);
    }
}