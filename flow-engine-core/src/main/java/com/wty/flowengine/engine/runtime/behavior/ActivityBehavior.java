package com.wty.flowengine.engine.runtime.behavior;

import com.wty.flowengine.engine.domain.Execution;

public interface ActivityBehavior {
    void execute(Execution execution);
}
