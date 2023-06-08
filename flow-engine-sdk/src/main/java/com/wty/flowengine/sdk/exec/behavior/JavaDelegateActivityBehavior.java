package com.wty.flowengine.sdk.exec.behavior;

import com.wty.flowengine.engine.common.api.JavaDelegate;
import com.wty.flowengine.sdk.exec.vo.Token;

public class JavaDelegateActivityBehavior extends FlowNodeActivityBehavior implements ActivityBehavior {

    private final JavaDelegate javaDelegate;

    public JavaDelegateActivityBehavior(JavaDelegate javaDelegate) {
        this.javaDelegate = javaDelegate;
    }

    @Override
    public void execute(Token token) {
        javaDelegate.execute(token);
        super.leave(token);
    }
}
