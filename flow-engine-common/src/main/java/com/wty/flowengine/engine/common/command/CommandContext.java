package com.wty.flowengine.engine.common.command;

import com.wty.flowengine.engine.common.AbstractEngineConfiguration;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommandContext {


    private AbstractEngineConfiguration engineConfiguration;
    private Command<?> command;
    private Map<Class<?>, SessionFactory> sessionFactories;
    private Map<Class<?>, Session> sessions = new HashMap<>();
    private Object result;
    private Exception exception;

    public CommandContext(Command<?> command) {
        this.command = command;
    }

    @SuppressWarnings({"unchecked"})
    public <T> T getSession(Class<T> sessionClass) {
        Session session = sessions.get(sessionClass);
        if (session == null) {
            SessionFactory sessionFactory = sessionFactories.get(sessionClass);
            if (sessionFactory == null) {
                throw new FlowEngineException("no session factory configured for " + sessionClass.getName());
            }
            session = sessionFactory.openSession(this);
            sessions.put(sessionClass, session);
        }
        return (T) session;
    }

    public void handleException(Exception e) {
        if (exception == null) {
            exception = e;
        } else {
            log.error("masked exception in command context. {}", e.getMessage());
        }
    }

    //------ getter and setter -------------

    public Command<?> getCommand() {
        return command;
    }

    public void setCommand(Command<?> command) {
        this.command = command;
    }

    public AbstractEngineConfiguration getEngineConfiguration() {
        return engineConfiguration;
    }

    public void setEngineConfiguration(AbstractEngineConfiguration engineConfiguration) {
        this.engineConfiguration = engineConfiguration;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public Map<Class<?>, SessionFactory> getSessionFactories() {
        return sessionFactories;
    }

    public void setSessionFactories(Map<Class<?>, SessionFactory> sessionFactories) {
        this.sessionFactories = sessionFactories;
    }

    public Map<Class<?>, Session> getSessions() {
        return sessions;
    }

    public void setSessions(Map<Class<?>, Session> sessions) {
        this.sessions = sessions;
    }
}
