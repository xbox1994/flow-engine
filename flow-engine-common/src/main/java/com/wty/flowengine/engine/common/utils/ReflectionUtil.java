package com.wty.flowengine.engine.common.utils;

import com.wty.flowengine.engine.common.api.FlowEngineException;

public class ReflectionUtil {


    public static Object instantiate(String className){
        try {
            Class<?> clazz = Class.forName(className);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new FlowEngineException("couldn't instantiate class " + className, e);
        }
    }
}
