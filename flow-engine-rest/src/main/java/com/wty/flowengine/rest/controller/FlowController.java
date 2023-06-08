package com.wty.flowengine.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wty.flowengine.api.FlowApi;
import com.wty.flowengine.api.request.StartFlowDto;
import com.wty.flowengine.api.request.TriggerDto;
import com.wty.flowengine.api.response.BaseResponse;
import com.wty.flowengine.engine.ProcessEngine;
import com.wty.flowengine.engine.domain.ActivityTask;
import com.wty.flowengine.engine.domain.Execution;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flow")
@Api(value = "flowController", tags = "流程api")
public class FlowController implements FlowApi {
    @Autowired
    ProcessEngine processEngine;

    @Override
    public BaseResponse<Long> startByKey(StartFlowDto startFlowDto) {
        Execution execution = processEngine.getRuntimeService()
            .startByKey(startFlowDto.getDefKey(), startFlowDto.getBizId(), startFlowDto.getVariables());
        return BaseResponse.ok(execution.getProcInstanceId());
    }

    @Override
    public BaseResponse<Void> complete(TriggerDto completeDto) {
        processEngine.getTaskService()
            .complete(completeDto.getDefKey(), completeDto.getBizId(), completeDto.getNodeId(),
                completeDto.getVariables());
        return BaseResponse.ok(null);
    }

    @Override
    public BaseResponse<Long> getTaskId(String defKey, String bizId, String nodeId) {
        ActivityTask task = processEngine.getTaskService().findTask(defKey, bizId, nodeId);
        if (task != null) {
            return BaseResponse.ok(task.getId());
        }
        return BaseResponse.ok(null);
    }

    public BaseResponse<Void> processUpload(@RequestBody String json) {
        JsonNode jsonNode;
        try {
            jsonNode = new ObjectMapper().readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        processEngine.getRepositoryService().deploy(jsonNode);

        return BaseResponse.ok(null);
    }

    public BaseResponse<Void> processDelete(Long id) {
        processEngine.getRepositoryService().deleteDeployment(id);
        return BaseResponse.ok(null);
    }
}
