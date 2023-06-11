package com.wty.flowengine.sdk.exec.behavior;

import com.wty.flowengine.rest.demo.test.model.ServiceTask;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.common.api.JavaDelegate;
import com.wty.flowengine.engine.common.utils.ReflectionUtil;

public class ServiceTaskBehaviorFactory {

    public static ActivityBehavior create(ServiceTask serviceTask) {
        String implementation = serviceTask.getImplementation();
        switch (implementation) {
            case ServiceTask.Implementation.JAVA_CLASS:
                return createClassDelegateBehavior(serviceTask);
            case ServiceTask.Implementation.JAVA_BEAN:
                throw new RuntimeException("JavaBean not implemented");
            case ServiceTask.Implementation.GROOVY:
                return createScriptTaskActivityBehavior(serviceTask);
            default:
                throw new FlowEngineException("Unsupported service implementation: " + implementation);
        }
    }

    private static ActivityBehavior createClassDelegateBehavior(ServiceTask serviceTask) {
        String className = serviceTask.getClassName();
        Object obj = ReflectionUtil.instantiate(className);
        if (obj instanceof JavaDelegate) {
            return new JavaDelegateActivityBehavior((JavaDelegate) obj);
        } else {
            throw new FlowEngineException("JavaClass '" + className + "' doesn't implement " + JavaDelegate.class);
        }
    }

    private static ActivityBehavior createScriptTaskActivityBehavior(ServiceTask serviceTask) {
        return new ScriptTaskActivityBehavior(serviceTask.getScript());
    }

}
