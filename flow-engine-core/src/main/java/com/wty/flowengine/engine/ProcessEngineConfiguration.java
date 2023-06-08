package com.wty.flowengine.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wty.flowengine.engine.common.AbstractEngineConfiguration;
import com.wty.flowengine.engine.common.command.CommandContextFactory;
import com.wty.flowengine.engine.common.command.CommandContextInterceptor;
import com.wty.flowengine.engine.common.command.DefaultCommandExecutor;
import com.wty.flowengine.engine.common.script.GroovyScriptExecutor;
import com.wty.flowengine.engine.domain.repository.*;
import com.wty.flowengine.engine.domain.repository.impl.*;
import com.wty.flowengine.engine.runtime.agenda.FlowEngineAgendaFactory;
import com.wty.flowengine.engine.runtime.behavior.ActivityBehaviorFactory;
import com.wty.flowengine.engine.runtime.behavior.DefaultActivityBehaviorFactory;
import com.wty.flowengine.engine.runtime.common.IDGenerator;
import com.wty.flowengine.engine.runtime.common.SnowFlakeIdGenerator;
import com.wty.flowengine.engine.runtime.interceptor.CommandInvoker;
import lombok.Getter;
import lombok.Setter;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.transaction.PlatformTransactionManager;

public class ProcessEngineConfiguration extends AbstractEngineConfiguration {
    @Getter
    @Setter
    private SqlSessionTemplate sqlSessionTemplate;

    @Getter
    @Setter
    private PlatformTransactionManager transactionManager;

    private final ProcessEngine processEngine = new ProcessEngine(this);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Getter
    private ProcessDefinitionRepository processDefinitionRepository;

    @Getter
    private ProcessInstanceRepository processInstanceRepository;

    @Getter
    private ExecutionRepository executionRepository;

    @Getter
    private DeploymentRepository deploymentRepository; // 既可以注入，也可以手动设置

    @Getter
    private ResourceRepository resourceRepository;

    @Getter
    private ActivityTaskRepository activityTaskRepository;

    @Getter
    ProcessEventLogRepository processEventLogRepository;

    @Getter
    private final GroovyScriptExecutor groovyScriptExecutor = new GroovyScriptExecutor();

    @Getter
    @Setter
    private IDGenerator idGenerator = new SnowFlakeIdGenerator();

    @Getter
    @Setter
    private ActivityBehaviorFactory activityBehaviorFactory;


    public DefaultCommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    public ProcessEngine build() {
        initRepositories();
        initSessionFactories();
        initCommandExecutor();
        initActivityBehaviorFactory();
        return processEngine;
    }

    private void initActivityBehaviorFactory() {
        if (activityBehaviorFactory == null) {
            this.activityBehaviorFactory = new DefaultActivityBehaviorFactory(null);
        }
    }

    private void initRepositories() {
        if (deploymentRepository == null) {
            deploymentRepository = new DeploymentRepositoryImpl(this);
        }
        if (processDefinitionRepository == null) {
            processDefinitionRepository = new ProcessDefinitionRepositoryImpl(this);
        }
        if (resourceRepository == null) {
            resourceRepository = new ResourceRepositoryImpl(this);
        }
        if (processInstanceRepository == null) {
            processInstanceRepository = new ProcessInstanceRepositoryImpl(this);
        }
        if (executionRepository == null) {
            executionRepository = new ExecutionRepositoryImpl(this);
        }
        if (activityTaskRepository == null) {
            activityTaskRepository = new ActivityTaskRepositoryImpl(this);
        }
        if (processEventLogRepository == null) {
            processEventLogRepository = new ProcessEventLogRepositoryImpl(this);
        }
    }

    private void initSessionFactories() {
        addSessionFactory(new FlowEngineAgendaFactory());
    }

    public void initCommandExecutor() {
        CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor();
        commandContextInterceptor.setEngineConfiguration(this);
        commandContextInterceptor.setCommandContextFactory(new CommandContextFactory(this));
        CommandInvoker commandInvoker = new CommandInvoker();
        commandContextInterceptor.setNext(commandInvoker);
        commandExecutor = new DefaultCommandExecutor(commandContextInterceptor);
    }

    //--------- getter and setter --------------

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public <T> T getMapper(Class<T> mapperInterface) {
        return this.sqlSessionTemplate.getMapper(mapperInterface);
    }
}
