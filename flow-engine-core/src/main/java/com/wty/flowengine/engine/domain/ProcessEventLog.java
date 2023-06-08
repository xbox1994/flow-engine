package com.wty.flowengine.engine.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@TableName(value = "flow_process_event_log", resultMap = "BaseResultMap")
public class ProcessEventLog {

    public interface Event {
        String PROCESS_STARTED = "process_started";
        String PROCESS_ENDED = "process_ended";
        String ACTIVITY_STARTED = "activity_started";
        String ACTIVITY_ENDED = "activity_ended";

        String EXECUTION_FAILED = "execution_failed";

        String TASK_CREATED = "task_created";
    }

    private Long id;
    private Long procInstId;

    private Long executionId;

    private String event;

    private String nodeId;

    private String nodeName;

    private Date time;

    private String exception;

    private Map<String, Object> variables;

    private Map<String, Object> extra;
}
