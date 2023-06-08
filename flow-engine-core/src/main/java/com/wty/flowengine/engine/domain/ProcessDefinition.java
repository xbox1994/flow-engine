package com.wty.flowengine.engine.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wty.flow.model.Process;
import com.wty.flowengine.converter.ProcessConverter;
import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.runtime.util.CommandContextUtil;
import lombok.Data;

import java.io.ByteArrayInputStream;

@Data
@TableName(value = "flow_process_definition", resultMap = "BaseResultMap")
public class ProcessDefinition {
    private Long id;

    /**
     * 流程名称，展示用
     */
    private String name;

    /**
     * 流程唯一标识，用于API调用
     */
    private String key;

    /**
     * 关联部署记录
     */
    private Long deploymentId;

    /**
     * 流程定义文件的名称
     */
    private String resourceName;

    /**
     * 流程版本号
     */
    private int version;

    /**
     * 保存了流程定义
     */
    @JsonIgnore // 期望展示的是流程定义的JSON，而不是流程定义对象序列化后的JSON
    private Process process;

    public Process getProcess() {
        ensureProcessLoaded();
        return process;
    }

    private void ensureProcessLoaded() {
        if (process == null) {
            ProcessEngineConfiguration configuration = CommandContextUtil.getProcessEngineConfiguration();
            Resource resource = configuration.getResourceRepository()
                    .selectByDeploymentIdAndName(this.getDeploymentId(), this.getResourceName());
            if (resource == null) {
                throw new FlowEngineException("流程定义文件缺失, id = " + id);
            }
            ByteArrayInputStream inputStream = new ByteArrayInputStream(resource.getBytes());
            this.process = ProcessConverter.convertToProcess(inputStream);
        }
    }
}
