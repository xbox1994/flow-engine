package com.wty.flowengine.engine.common.command;

import java.util.Stack;

/**
 * 工具类，管理线程本地存储上下文对象
 */
public class Context {

    private static final ThreadLocal<Stack<CommandContext>> commandContextThreadLocal = new ThreadLocal<>();

    public static CommandContext getCommandContext() {
        Stack<CommandContext> stack = getStack(commandContextThreadLocal);
        if (stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }

    public static void setCommandContext(CommandContext commandContext) {
        getStack(commandContextThreadLocal).push(commandContext);
    }

    public static void removeCommandContext() {
        getStack(commandContextThreadLocal).pop();
    }

    private static Stack<CommandContext> getStack(final ThreadLocal<Stack<CommandContext>> threadLocal) {
        Stack<CommandContext> stack = threadLocal.get();
        if (stack == null) {
            stack = new Stack<>();
            threadLocal.set(stack);
        }
        return stack;

    }


}
