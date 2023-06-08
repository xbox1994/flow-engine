package com.wty.flowengine.engine.domain.repository.impl;

import com.wty.flowengine.engine.ProcessEngineConfiguration;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @param <M> Mapper
 */
public abstract class BaseRepository<M> {

    protected M mapper;

    protected final ProcessEngineConfiguration processEngineConfiguration;

    @SuppressWarnings("unchecked")
    public BaseRepository(ProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
        this.mapper = (M) this.processEngineConfiguration.getMapper(getMapperClass());
    }

    private Class<?> getMapperClass() {
        // 获取泛型参数M的Class
        Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        Type mapperType = typeArguments[0];
        return (Class<?>) mapperType;
    }

    protected long genId() {
        return processEngineConfiguration.getIdGenerator().genId();
    }
}
