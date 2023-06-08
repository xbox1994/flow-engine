package com.wty.flowengine.engine.runtime.cmd;

import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.common.command.Command;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.domain.ActivityTask;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.runtime.util.CommandContextUtil;

import java.util.Map;

/**
 * 重新执行Execution实例，会获取当前对应的节点，然后执行节点Behavior的execute方法。<br/>
 * 在人工触发ServiceTask的场景（manual=true）, 调用该方法继续执行节点逻辑。<br/>
 */
public class CompleteActivityTaskCmd implements Command<Void> {

    private final ActivityTask activityTask;

    private final Map<String, Object> variables;

    public CompleteActivityTaskCmd(ActivityTask activityTask, Map<String, Object> variables) {
        this.activityTask = activityTask;
        this.variables = variables;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        ProcessEngineConfiguration cfg = CommandContextUtil.getProcessEngineConfiguration();
        Execution execution = cfg.getExecutionRepository().findById(activityTask.getExecutionId());
        if (execution == null) {
            throw new RuntimeException("Execution not found");
        }
        cfg.getCommandExecutor().executeAsync(commandContext1 -> {
            execution.setVariables(variables);
            CommandContextUtil.getAgenda().planContinueExecutionOperation(execution, false);
            cfg.getActivityTaskRepository().getMapper().deleteById(activityTask); // todo 优化这块的删除逻辑
            return null;
        });
        return null;
    }
}
