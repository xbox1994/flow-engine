package com.wty.flowengine.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StartFlowDto {

    /**
     * 流程定义
     */
    private String defKey;

    /**
     * 业务记录
     */
    private String bizId;

    /**
     * 变量
     */
    private Map<String, Object> variables;
}
