package com.wty.flowengine.engine.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wty.flow.model.FlowElement;
import com.wty.flow.model.SequenceFlow;
import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.common.api.VariableScope;
import com.wty.flowengine.engine.runtime.util.CommandContextUtil;
import com.wty.flowengine.engine.runtime.util.ProcessDefinitionUtil;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * 记录流程执行信息(代表当前执行路径)
 */
@Getter
@Setter
@TableName(value = "flow_execution", resultMap = "BaseResultMap")
public class Execution implements VariableScope {

    private Long id;

    private Long procDefinitionId;

    private Long procInstanceId;

    /**
     * 业务对象ID
     */
    private String bizId;

    /**
     * 当前活动节点的ID
     */
    private String activityId;


    /**
     * 当前活动节点的名称
     */
    private String activityName;

    private Long parentId;

    private Execution parent;

    /**
     * 当前执行的活动节点
     */
    private FlowElement currentFlowElement;

    /**
     * 当前 Execution 作用域的变量
     */
    private Map<String, Object> variables = new HashMap<>();


    /**
     * 记录流程定义信息，在执行过程中会访问流程定义
     */
    private ProcessDefinition processDefinition;


    public Execution createChild(SequenceFlow outgoingSequenceFlow) {
        ProcessEngineConfiguration configuration = CommandContextUtil.getProcessEngineConfiguration();
        Execution execution = configuration.getExecutionRepository().create();
        execution.setParent(this);
        execution.setCurrentFlowElement(outgoingSequenceFlow);
        execution.setProcessDefinition(this.getProcessDefinition());
        return execution;
    }

    public void setCurrentFlowElement(FlowElement currentFlowElement) {
        this.currentFlowElement = currentFlowElement;
        this.activityId = currentFlowElement.getId();
        this.activityName = currentFlowElement.getName();
    }

    public void setParent(Execution parent) {
        this.parent = parent;
        this.parentId = parent.getId();
    }

    public void setProcessDefinition(@Nonnull ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
        this.procDefinitionId = processDefinition.getId();
    }

    @Override
    public Object getVariable(String name) {
        return getVariablesInternal().get(name);
    }

    @Override
    public void setVariable(String name, Object value) {
        getVariablesInternal().put(name, value);
    }

    public FlowElement getCurrentFlowElement() {
        if (currentFlowElement != null) {
            return currentFlowElement;
        }
        if (activityId != null) {
            ensureProcessDefinitionInitialized();
            currentFlowElement = processDefinition.getProcess().getFlowElement(activityId);
        }
        return currentFlowElement;
    }

    private void ensureProcessDefinitionInitialized() {
        if (processDefinition == null) {
            processDefinition = ProcessDefinitionUtil.getProcessDefinition(procDefinitionId);
        }
    }

    // TODO 默认情况下，所有的变量都是保存在顶层对象上，即不支持局部变量；如果需要支持局部变量，可以在这里做一些处理

    @Override
    public Map<String, Object> getVariables() {
        return getVariablesInternal();
    }

    @Override
    public void removeVariable(String name) {
        getVariablesInternal().remove(name);
    }

    @Override
    public void removeVariables() {
        getVariablesInternal().clear();
    }

    @Override
    public boolean hasVariable(String name) {
        return getVariablesInternal().containsKey(name);
    }

    @Override
    public void setVariables(Map<String, Object> variables) {
        if (variables != null) {
            variables.forEach(this::setVariable);
        }
    }

    private Map<String, Object> getVariablesInternal() {
        if (parent == null) {
            return this.variables;
        }
        return parent.getVariablesInternal();
    }

    /**
     * 获取当前 Execution 当前能访问的所有变量的集合
     */
    public Map<String, Object> getScopeVariables() {
        Map<String, Object> result = new HashMap<>();
        addScopedVariables(result);
        return result;
    }

    private void addScopedVariables(Map<String, Object> scopeVariables) {
        if (this.variables != null) {
            for (Map.Entry<String, Object> entry : this.variables.entrySet()) {
                if (!scopeVariables.containsKey(entry.getKey())) {
                    scopeVariables.put(entry.getKey(), entry.getValue());
                }
            }
        }
        if (parent != null) {
            parent.addScopedVariables(scopeVariables);
        }
    }
}
