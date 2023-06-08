package com.wty.flowengine.engine.runtime.common;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class SnowFlakeIdGenerator implements IDGenerator {

    private final Snowflake snowflake = IdUtil.getSnowflake();

    @Override
    public Long genId() {
        return snowflake.nextId();
    }
}
