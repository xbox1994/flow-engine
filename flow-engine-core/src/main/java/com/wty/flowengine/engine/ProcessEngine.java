package com.wty.flowengine.engine;

import com.wty.flowengine.engine.service.HistoryService;
import com.wty.flowengine.engine.service.RepositoryService;
import com.wty.flowengine.engine.service.RuntimeService;
import com.wty.flowengine.engine.service.TaskService;
import lombok.Data;

@Data
public class ProcessEngine {

    private final ProcessEngineConfiguration processEngineConfiguration;

    private RuntimeService runtimeService;
    private RepositoryService repositoryService;
    private HistoryService historyService;

    private TaskService taskService;

    public ProcessEngine(ProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
        this.runtimeService = new RuntimeService(processEngineConfiguration);
        this.repositoryService = new RepositoryService(processEngineConfiguration);
        this.historyService = new HistoryService(processEngineConfiguration);
        this.taskService = new TaskService(processEngineConfiguration);
    }

}
