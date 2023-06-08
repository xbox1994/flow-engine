package com.wty.flowengine.engine.runtime.behavior;

import com.wty.flow.model.FlowElement;

public interface ActivityBehaviorFactory {
    ActivityBehavior create(FlowElement flowElement);
}
