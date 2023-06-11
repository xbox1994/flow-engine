package com.wty.flowengine.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class TriggerDto {

    private String defKey;

    private String bizId;

    private String nodeId;

    private Map<String,Object> variables;
}
