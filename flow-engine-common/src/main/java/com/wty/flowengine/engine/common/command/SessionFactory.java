package com.wty.flowengine.engine.common.command;

public interface SessionFactory {

    Class<?> getSessionType();

    Session openSession(CommandContext commandContext);

}
