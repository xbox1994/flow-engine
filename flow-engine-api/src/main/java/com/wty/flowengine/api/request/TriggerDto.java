package com.wty.flowengine.api.request;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class TriggerDto {

    private String defKey;

    private String bizId;

    private String nodeId;

    private Map<String,Object> variables;
}
