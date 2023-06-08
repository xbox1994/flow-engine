package com.wty.flowengine.engine.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "flow_activity_task", resultMap = "BaseResultMap")
public class ActivityTask {
    private long id;
    private long procInstId;
    private long executionId;

    private String activityId;
    private String activityName;
}
