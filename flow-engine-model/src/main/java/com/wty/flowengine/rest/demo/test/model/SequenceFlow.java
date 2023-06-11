package com.wty.flowengine.rest.demo.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SequenceFlow extends FlowElement {
    /**
     * 源节点ID
     */
    protected String sourceId;

    /**
     * 目标节点ID
     */
    protected String targetId;

    /**
     * 条件
     */
    protected String condition;


    @JsonIgnore
    protected FlowElement target;

    @JsonIgnore // 否则JSON序列化会导致无限递归
    protected FlowElement source;


}
