package com.wty.flowengine.engine.runtime.agenda.operation;

import com.wty.flow.model.Activity;
import com.wty.flow.model.FlowElement;
import com.wty.flow.model.FlowNode;
import com.wty.flow.model.SequenceFlow;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.common.command.CommandContext;
import com.wty.flowengine.engine.domain.ActivityTask;
import com.wty.flowengine.engine.domain.Execution;
import com.wty.flowengine.engine.domain.repository.ActivityTaskRepository;
import com.wty.flowengine.engine.runtime.behavior.ActivityBehavior;
import com.wty.flowengine.engine.runtime.util.CommandContextUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContinueProcessOperation extends AbstractOperation {

    private boolean checkManual = true;

    public ContinueProcessOperation(CommandContext commandContext, Execution execution) {
        super(commandContext, execution);
    }

    public ContinueProcessOperation(CommandContext commandContext, Execution execution, boolean checkManual) {
        super(commandContext, execution);
        this.checkManual = checkManual;
    }

    @Override
    public void run() {
        FlowElement currentFlowElement = getCurrentFlowElement();
        if (currentFlowElement == null) {
            throw new FlowEngineException("currentFlowElement is null");
        }
        if (currentFlowElement instanceof FlowNode) {
            continueThroughFlowNode((FlowNode) currentFlowElement);
        } else if (currentFlowElement instanceof SequenceFlow) {
            continueThroughSequenceFlow((SequenceFlow) currentFlowElement);
        } else {
            throw new FlowEngineException("unknown flowElement type: " + currentFlowElement.getClass());
        }
    }


    public void continueThroughFlowNode(FlowNode flowNode) {
        // execute listener、firing event、record history ...
        if(flowNode instanceof Activity && ((Activity) flowNode).isManual() && checkManual){
            // 手动任务
            executeManualActivity((Activity) flowNode);
            return;
        }
        executeSynchronous(flowNode);
    }

    private void executeManualActivity(Activity activity) {
        ActivityTaskRepository repo = CommandContextUtil.getProcessEngineConfiguration().getActivityTaskRepository();
        ActivityTask task = repo.create();
        task.setProcInstId(execution.getProcInstanceId());
        task.setExecutionId(execution.getId());
        task.setActivityId(activity.getId());
        task.setActivityName(activity.getName());
        CommandContextUtil.getProcessEngineConfiguration().getProcessEventLogRepository().recordActivityTaskCreated(execution, task);
    }

    private void executeSynchronous(FlowNode flowNode) {
        // record activity started; execute listener; create events ...
        CommandContextUtil.getProcessEngineConfiguration().getProcessEventLogRepository().recordActivityStart(execution, flowNode);
        // execute behavior
        ActivityBehavior activityBehavior = (ActivityBehavior) flowNode.getBehavior();
        if (activityBehavior != null) {
            executeActivityBehavior(activityBehavior, flowNode);
        } else {
            log.debug("No activityBehavior on activity '{}' with execution {}", flowNode.getId(), execution.getId());
            agenda.planTakeOutgoingSequenceFlowsOperation(execution, true);
        }
    }

    private void executeActivityBehavior(ActivityBehavior activityBehavior, FlowNode flowNode) {
        log.debug("Executing activityBehavior {} on activity '{}' with execution {}", activityBehavior.getClass(), flowNode.getId(), execution.getId());
        // 发送事件「ACTIVITY_STARTED」

        activityBehavior.execute(execution);
    }


    public void continueThroughSequenceFlow(SequenceFlow sequenceFlow) {
        // execute listener、firing event、record history ...
        // 发送「SEQUENCE_TAKEN」事件
        FlowElement target = sequenceFlow.getTarget();
        execution.setCurrentFlowElement(target);
        agenda.planContinueProcessOperation(execution);
    }

}
