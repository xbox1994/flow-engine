package com.wty.flowengine.engine.common.script;

import com.google.common.hash.Hashing;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;
import groovy.transform.ConditionalInterrupt;
import groovy.transform.ThreadInterrupt;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.kohsuke.groovy.sandbox.SandboxTransformer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GroovyScriptExecutor {

    private final GroovyScriptClassCache scriptCache;

    public GroovyScriptExecutor() {
        scriptCache = new GroovyScriptClassCache();
    }

    public Object execute(String scriptText, Map<String, Object> context) {
        Class<?> scriptClass = getScriptClass(scriptText);
        Script script = InvokerHelper.createScript(scriptClass, new Binding(context));
        // 注册执行拦截器
        new GroovyNotSupportedInterceptor().register();
        try {
            return script.run();
        } catch (Exception e) {
            throw new FlowEngineException("Script execution failed", e);
        }
    }

    private Class<?> getScriptClass(String scriptText) {
        String cacheKey = genCacheKey(scriptText);
        Class<?> scriptClass = scriptCache.get(cacheKey);
        if (scriptClass == null) {
            scriptClass = parseScript(scriptText);
        }
        scriptCache.put(cacheKey, scriptClass);
        return scriptClass;
    }

    private Class<?> parseScript(String scriptText) {
        // 使用沙箱编译
        CompilerConfiguration configuration = buildCompilerConfiguration(5L);

        try (GroovyClassLoader loader = new GroovyClassLoader(this.getClass().getClassLoader(), configuration)) {
            return loader.parseClass(scriptText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String genCacheKey(String scriptText) {
        return Hashing.goodFastHash(128)
                .hashBytes(scriptText.getBytes(StandardCharsets.UTF_8))
                .toString();

    }

    public CompilerConfiguration buildCompilerConfiguration(long timeoutSeconds) {
        CompilerConfiguration config = new CompilerConfiguration();

        // 添加线程终端拦截器，可拦截循环体（for， while），方法和闭包的首指令
        config.addCompilationCustomizers(new ASTTransformationCustomizer(ThreadInterrupt.class));

        Map<String, Object> timeoutArgs = new HashMap<>();
        timeoutArgs.put("value",
                new ClosureExpression(Parameter.EMPTY_ARRAY,
                        new ExpressionStatement(
                                new MethodCallExpression(
                                        new ClassExpression(ClassHelper.make(ConditionInterceptor.class)),
                                        "checkTimeout",
                                        new ConstantExpression(timeoutSeconds))
                        )
                ));
        config.addCompilationCustomizers(new ASTTransformationCustomizer(timeoutArgs, ConditionalInterrupt.class));

        //沙盒环境
        config.addCompilationCustomizers(new SandboxTransformer());

        return config;
    }
}
