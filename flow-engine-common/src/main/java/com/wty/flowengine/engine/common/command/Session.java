package com.wty.flowengine.engine.common.command;

public interface Session {
    void flush();

    void close();
}
