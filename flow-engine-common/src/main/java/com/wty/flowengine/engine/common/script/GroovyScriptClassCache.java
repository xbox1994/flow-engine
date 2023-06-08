package com.wty.flowengine.engine.common.script;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

class GroovyScriptClassCache {

    private final Cache<String, Class<?>> cache;

    public GroovyScriptClassCache() {
        // build cache
        cache = CacheBuilder.newBuilder()
                .maximumSize((long) 1e6)
                .expireAfterAccess(10L, TimeUnit.DAYS)
                .build();
    }

    public Class<?> get(String key) {
        return cache.getIfPresent(key);
    }

    /**
     * @param key key的生成规则由使用方提供
     */
    public void put(String key, Class<?> scriptClass) {
        cache.put(key, scriptClass);
    }
}
