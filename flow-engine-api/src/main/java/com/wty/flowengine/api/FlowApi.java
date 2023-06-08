package com.wty.flowengine.api;

import com.wty.flowengine.api.request.StartFlowDto;
import com.wty.flowengine.api.request.TriggerDto;
import com.wty.flowengine.api.response.BaseResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 流程api
 */
public interface FlowApi {
    @PostMapping({"create"})
    @ApiOperation("流程创建")
    BaseResponse<Void> processUpload(@RequestBody String json);

    @GetMapping({"delete"})
    @ApiOperation("流程删除")
    BaseResponse<Void> processDelete(Long id);

    @PostMapping("runtime/start")
    @ApiOperation("发起流程")
    BaseResponse<Long> startByKey(@RequestBody StartFlowDto startFlowDto);

    @PostMapping("runtime/node/trigger")
    @ApiOperation("驱动流程")
    BaseResponse<Void> complete(@RequestBody TriggerDto completeDto);

    @PostMapping({"runtime/getTask/{defKey}/{bizId}/{nodeId}"})
    @ApiOperation("获取流程任务id")
    BaseResponse<Long> getTaskId(@PathVariable("defKey") String defKey, @PathVariable("bizId") String bizId,
        @PathVariable("nodeId") String nodeId);
}
