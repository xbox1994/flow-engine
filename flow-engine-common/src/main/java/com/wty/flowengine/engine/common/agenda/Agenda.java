package com.wty.flowengine.engine.common.agenda;

import com.wty.flowengine.engine.common.command.Session;

public interface Agenda extends Session {

    void planOperation(Runnable operation);

    boolean isEmpty();

    Runnable getNextOperation();
}
