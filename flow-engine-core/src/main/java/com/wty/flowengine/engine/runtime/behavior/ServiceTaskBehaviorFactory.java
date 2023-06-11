package com.wty.flowengine.engine.runtime.behavior;

import com.wty.flowengine.rest.demo.test.model.ServiceTask;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import com.wty.flowengine.engine.common.api.JavaDelegate;
import com.wty.flowengine.engine.common.utils.ReflectionUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Nonnull;

public class ServiceTaskBehaviorFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public ServiceTaskBehaviorFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ActivityBehavior create(ServiceTask serviceTask) {
        String implementation = serviceTask.getImplementation();
        switch (implementation) {
            case ServiceTask.Implementation.JAVA_CLASS:
                return createClassDelegateBehavior(serviceTask);
            case ServiceTask.Implementation.JAVA_BEAN:
                return new JavaDelegateActivityBehavior(applicationContext.getBean(serviceTask.getBeanName(), JavaDelegate.class));
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

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
