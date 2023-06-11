package com.wty.flowengine.sdk.exec.behavior;

import com.wty.flowengine.rest.demo.test.model.FlowElement;

public interface ActivityBehaviorFactory {
    ActivityBehavior create(FlowElement flowElement);

}
