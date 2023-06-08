package com.wty.flowengine.engine.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.annotation.Nullable;

@Data
@TableName(value = "flow_resource", resultMap = "BaseResultMap")
public class Resource {
    private Long id;
    /**
     * 资源名称
     */
    private String name;

    /**
     * 关联的部署记录
     */
    @Nullable
    private Long deploymentId;

    /**
     * 具体的资源内容
     */
    private byte[] bytes;
}
