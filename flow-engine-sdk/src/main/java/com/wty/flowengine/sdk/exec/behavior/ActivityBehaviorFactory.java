package com.wty.flowengine.sdk.exec.behavior;

import com.wty.flow.model.FlowElement;

public interface ActivityBehaviorFactory {
    ActivityBehavior create(FlowElement flowElement);

}
