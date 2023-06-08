package com.wty.flowengine.engine.common.script;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ConditionInterceptor {
    public static boolean checkTimeout(int timeoutSec) {
        boolean flag = ThreadLocalUtils.getStartTime() + timeoutSec * 1000L < System.currentTimeMillis();
        if (flag) {
            System.err.printf("[%s] Execution timed out after %s seconds. Start Time:%s%n",
                    ThreadLocalUtils.get("scriptName"),
                    timeoutSec,
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(ThreadLocalUtils.getStartTime()), ZoneOffset.ofHours(8))
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        return flag;
    }
}
