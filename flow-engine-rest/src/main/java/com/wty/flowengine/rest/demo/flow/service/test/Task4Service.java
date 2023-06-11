package com.wty.flowengine.rest.demo.flow.service.test;


import com.wty.flowengine.engine.common.api.JavaDelegate;
import com.wty.flowengine.engine.common.api.VariableScope;
import org.springframework.stereotype.Service;

@Service
public class Task4Service implements JavaDelegate {

    @Override
    public void execute(VariableScope variableScope) {
        String outwardCreateMessage = String.valueOf(variableScope.getVariable("outwardCreateMessage"));
        System.out.println("task4" + outwardCreateMessage);
    }
}
