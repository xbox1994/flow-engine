package com.wty.flowengine.engine.common.script;

import org.kohsuke.groovy.sandbox.GroovyInterceptor;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GroovyNotSupportedInterceptor extends GroovyInterceptor {
    public static final List<String> defaultMethodBlacklist = Arrays.asList("getClass", "class", "wait",
            "notify", "notifyAll", "invokeMethod", "finalize");
    public static final List<Class<?>> defaultClassBlacklist = Collections.singletonList(File.class);

    /**
     * 静态方法拦截
     */
    @Override
    public Object onStaticCall(GroovyInterceptor.Invoker invoker, Class receiver, String method,
                               Object... args) throws Throwable {
        if (receiver == System.class && "exit".equals(method)) {
            // System.exit(0)
            throw new SecurityException("No call on System.exit() please");
        } else if (receiver == Runtime.class) {
            // 通过Java的Runtime.getRuntime().exec()方法执行shell, 操作服务器…
            throw new SecurityException("No call on RunTime please");
        } else if (receiver == Class.class && "forName".equals(method)) {
            // Class.forName
            throw new SecurityException("No call on Class please");
        }
        return super.onStaticCall(invoker, receiver, method, args);
    }

    /**
     * 普通方法拦截
     */
    @Override
    public Object onMethodCall(GroovyInterceptor.Invoker invoker, Object receiver, String method,
                               Object... args) throws Throwable {
        if (defaultMethodBlacklist.contains(method)) {
            throw new SecurityException("Not support method: " + method);
        } else if (defaultClassBlacklist.contains(receiver.getClass())) {
            throw new SecurityException("Not support class: " + receiver.getClass());
        }
        return super.onMethodCall(invoker, receiver, method, args);
    }

    public Object onNewInstance(Invoker invoker, Class receiver, Object... args) throws Throwable {
        if (defaultClassBlacklist.contains(receiver)) {
            throw new SecurityException("Not support class: " + receiver);
        }
        return invoker.call(receiver, null, args);
    }
}
