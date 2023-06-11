package com.wty.flowengine.rest.demo.test.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallActivity extends Activity {

    /**
     * 调用的流程id
     */
    private String processId;
}
