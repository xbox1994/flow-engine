package com.wty.flowengine.rest.demo.flow.client;

import com.wty.flowengine.api.FlowApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "flow-svc", contextId = "flowClient", url = "localhost:7001", path = "/flow")
public interface FlowClient extends FlowApi {
}
