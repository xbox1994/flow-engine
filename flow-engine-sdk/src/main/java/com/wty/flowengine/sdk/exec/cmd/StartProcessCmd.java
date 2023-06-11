package com.wty.flowengine.sdk.exec.cmd;

import com.wty.flowengine.rest.demo.test.model.FlowElement;
import com.wty.flowengine.rest.demo.test.model.FlowNode;
import com.wty.flowengine.engine.common.command.Command;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.sdk.ProcessExecutorConfiguration;
import com.wty.flowengine.sdk.exec.util.CommandContextUtil;
import com.wty.flowengine.sdk.exec.vo.Token;

import java.util.List;
import java.util.Map;

public class StartProcessCmd implements Command<Void> {

    private final Process process;
    private final Map<String, Object> variables;

    public StartProcessCmd(Process process, Map<String, Object> variables) {
        this.process = process;
        this.variables = variables;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        parseProcess(commandContext);
        Token token = Token.create(process, process.getStart(), variables);
        CommandContextUtil.getAgenda().planContinueProcessOperation(token);
        return null;
    }

    private void parseProcess(CommandContext commandContext) {
        List<FlowElement> flowElements = process.getFlowElements();
        ProcessExecutorConfiguration configuration = (ProcessExecutorConfiguration) commandContext.getEngineConfiguration();
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof FlowNode) {
                Object behavior = configuration.getActivityBehaviorFactory().create(flowElement);
                ((FlowNode) flowElement).setBehavior(behavior);
            }
        }
    }

}
