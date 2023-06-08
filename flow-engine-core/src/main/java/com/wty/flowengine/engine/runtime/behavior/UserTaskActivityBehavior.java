package com.wty.flowengine.engine.runtime.behavior;

import com.wty.flowengine.engine.domain.Execution;

public class UserTaskActivityBehavior extends FlowNodeActivityBehavior {
    @Override
    public void execute(Execution execution) {
        super.leave(execution);
    }
}
