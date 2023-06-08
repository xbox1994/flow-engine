package com.wty.flowengine.engine;

import com.google.common.collect.ImmutableMap;
import com.wty.flowengine.engine.common.script.GroovyScriptExecutor;
import org.junit.jupiter.api.Test;

public class GroovyScriptExecutorTest {

    GroovyScriptExecutor executor = new GroovyScriptExecutor();

    @Test
    public void testExec() {
        ImmutableMap<String, Object> variables = ImmutableMap.of("ready", true);
        String script = "if(ready){\n" +
                "    println \"Ready!\"\n" +
                "}else {\n" +
                "    throw new RuntimeException(\"not ready\")\n" +
                "}";
        executor.execute(script, variables);
    }
}
