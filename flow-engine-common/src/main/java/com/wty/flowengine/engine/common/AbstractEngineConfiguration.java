package com.wty.flowengine.engine.common;

import com.wty.flowengine.engine.common.command.DefaultCommandExecutor;
import com.wty.flowengine.engine.common.command.SessionFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEngineConfiguration {

    protected DefaultCommandExecutor commandExecutor;

    private final Map<Class<?>, SessionFactory> sessionFactories = new HashMap<>();

    public Map<Class<?>, SessionFactory> getSessionFactories() {
        return sessionFactories;
    }


    public void addSessionFactory(SessionFactory sessionFactory) {
        sessionFactories.put(sessionFactory.getSessionType(), sessionFactory);
    }


}
