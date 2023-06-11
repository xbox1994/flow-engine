package com.wty.flowengine.engine.common.script;

import org.junit.jupiter.api.Test;

public class GroovyScriptExecutorTest {

    GroovyScriptExecutor executor = new GroovyScriptExecutor();

    @Test
    public void testRun() {
        long t0 = System.currentTimeMillis();
        executor.execute("println 'Hello'", null);
        t0 = printCostSince(t0);
        executor.execute("println 'Hello'", null);
        printCostSince(t0);
    }

    private long printCostSince(long t0) {
        long t1 = System.currentTimeMillis();
        System.out.println("cost: " + (t1 - t0) + " millis");
        return t1;
    }
}