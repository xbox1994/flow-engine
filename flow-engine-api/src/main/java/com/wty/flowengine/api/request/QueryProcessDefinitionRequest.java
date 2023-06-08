package com.wty.flowengine.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("分页信息")
@Data
public class QueryProcessDefinitionRequest {
    @ApiModelProperty("页码，从1开始")
    private int page;
    @ApiModelProperty("每页大小")
    private int size;

    // todo 增加查询条件
    @ApiModelProperty("过滤条件")
    private String filter;
}
