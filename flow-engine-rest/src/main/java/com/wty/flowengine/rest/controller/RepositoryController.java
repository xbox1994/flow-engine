package com.wty.flowengine.rest.controller;

import com.wty.flowengine.api.request.DeployRequest;
import com.wty.flowengine.api.request.QueryProcessDefinitionRequest;
import com.wty.flowengine.api.response.BaseResponse;
import com.wty.flowengine.engine.ProcessEngine;
import com.wty.flowengine.engine.domain.Deployment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/repository")
@Api("Repository")
public class RepositoryController {

    @Resource
    ProcessEngine processEngine;

    @GetMapping("/")
    public BaseResponse<String> hello() {
        return BaseResponse.ok("repository");
    }

    @PostMapping("/deploy")
    @ApiOperation("部署一个流程定义")
    public BaseResponse<Object> deploy(@RequestBody DeployRequest request) {
        Deployment deployment = processEngine.getRepositoryService().deploy(request.getJson());
        return BaseResponse.ok(deployment);
    }

    @GetMapping("/process-definitions")
    @ApiOperation("获取所有流程定义")
    public BaseResponse<Object> getProcessDefinitions(@RequestBody QueryProcessDefinitionRequest request) {
        return BaseResponse.ok(processEngine.getRepositoryService());
    }
}
