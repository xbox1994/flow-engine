package com.wty.flowengine.engine.service;

import com.wty.flowengine.engine.ProcessEngineConfiguration;

public class HistoryService {
    // 查看流程实变量

    private ProcessEngineConfiguration processEngineConfiguration;

    public HistoryService(ProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
    }

    public ProcessEngineConfiguration getProcessEngineConfiguration() {
        return processEngineConfiguration;
    }

    public HistoryService setProcessEngineConfiguration(ProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
        return this;
    }

}
