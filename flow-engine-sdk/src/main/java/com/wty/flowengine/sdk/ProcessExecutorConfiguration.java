package com.wty.flowengine.sdk;

import com.wty.flowengine.engine.common.AbstractEngineConfiguration;
import com.wty.flowengine.engine.common.api.FlowScriptEngine;
import com.wty.flowengine.engine.common.command.*;
import com.wty.flowengine.sdk.exec.agenda.ProcessExecutorAgendaFactory;
import com.wty.flowengine.sdk.exec.behavior.ActivityBehaviorFactory;
import com.wty.flowengine.sdk.exec.behavior.DefaultActivityBehaviorFactory;
import com.wty.flowengine.sdk.exec.interceptor.CommandInvoker;
import com.wty.flowengine.sdk.exec.script.GroovyScriptEngine;

public class ProcessExecutorConfiguration extends AbstractEngineConfiguration {
    public ProcessExecutor processExecutor = new ProcessExecutor(this);

    private CommandExecutor commandExecutor;

    private ActivityBehaviorFactory activityBehaviorFactory;

    private FlowScriptEngine scriptEngine;

    public ProcessExecutor build() {
        initSessionFactories();
        initCommandExecutor();
        initActivityBehaviorFactory();
        initScriptEngine();
        return processExecutor;
    }


    private void initSessionFactories() {
        addSessionFactory(new ProcessExecutorAgendaFactory());
    }

    private void initCommandExecutor() {
        CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor();
        commandContextInterceptor.setEngineConfiguration(this);
        commandContextInterceptor.setCommandContextFactory(new CommandContextFactory(this));
        CommandInvoker commandInvoker = new CommandInvoker();
        commandContextInterceptor.setNext(commandInvoker);
        commandExecutor = new DefaultCommandExecutor(commandContextInterceptor);
    }

    private void initActivityBehaviorFactory() {
        this.activityBehaviorFactory = new DefaultActivityBehaviorFactory();
    }

    private void initScriptEngine() {
        this.scriptEngine = new GroovyScriptEngine();
    }

    // getter and setter

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    public ActivityBehaviorFactory getActivityBehaviorFactory() {
        return activityBehaviorFactory;
    }

    public FlowScriptEngine getScriptEngine() {
        return scriptEngine;
    }
}
