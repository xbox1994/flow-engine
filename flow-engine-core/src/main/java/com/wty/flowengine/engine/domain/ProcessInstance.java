package com.wty.flowengine.engine.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


/**
 * 运行中和历史的流程实例
 */
@Data
@TableName(value = "flow_process_instance", resultMap = "BaseResultMap")
public class ProcessInstance {

    public enum Status {
        RUNNING,
        ENDED, // 正常结束
        TERMINATED, // 异常终止
        CANCELED // 人工取消
    }

    private long id;

    /**
     * 关联的执行对象的ID
     */
    private long executionId;

    /**
     * 流程发起人
     */
    private String startUserId;

    private long procDefId;

    private String procDefKey;

    private String startActivityId;

    private String endActivityId;

    private Date startTime;

    private Date endTime;

    private Status status;

    private String remark;

    //---------

    private ProcessDefinition processDefinition;


    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
        this.procDefId = processDefinition.getId();
        this.procDefKey = processDefinition.getKey();
    }
}
