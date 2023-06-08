package com.wty.flowengine.engine.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "flow_deployment", resultMap = "BaseResultMap")
public class Deployment {

    private Long id;

    private Date deployTime;

    private Long parentDeploymentId;

}
