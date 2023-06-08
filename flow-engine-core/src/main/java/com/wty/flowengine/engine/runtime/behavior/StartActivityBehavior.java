package com.wty.flowengine.engine.runtime.behavior;

import com.wty.flowengine.engine.domain.Execution;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartActivityBehavior extends FlowNodeActivityBehavior {

    @Override
    public void execute(Execution execution) {
        leave(execution);
    }
}
