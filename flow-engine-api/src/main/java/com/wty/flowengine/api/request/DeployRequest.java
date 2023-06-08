package com.wty.flowengine.api.request;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("部署流程请求体")
public class DeployRequest {

    @ApiModelProperty("JSON格式的流程定义内容")
    private JsonNode json;
}
