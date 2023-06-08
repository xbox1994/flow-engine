package com.wty.flowengine.sdk.exec.behavior;

import com.wty.flowengine.sdk.exec.vo.Token;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartActivityBehavior extends FlowNodeActivityBehavior {

    @Override
    public void execute(Token token) {
        System.out.println("执行「开始」节点：" + token.getCurrentFlowElement());
        leave(token);
    }
}
