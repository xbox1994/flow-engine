package com.wty.flowengine.rest.demo.test.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceTask extends Activity {
    public static class Implementation {
        public static final String JAVA_CLASS = "JavaClass";
        public static final String JAVA_BEAN = "JavaBean";
        public static final String GROOVY = "Groovy";

    }

    private String implementation;
    /**
     * 实现方式为JavaClass时，指定类的全路径名
     */
    private String className;

    /**
     * 实现方式为groovy时，保存groovy脚本的内容
     */
    private String script;

    /**
     * 实现方式为JavaBean时，指定bean的名称
     */
    private String beanName;

}
