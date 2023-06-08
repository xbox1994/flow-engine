package com.wty.flowengine.engine.common.command;

import com.wty.flowengine.engine.common.AbstractEngineConfiguration;
import com.wty.flowengine.engine.common.api.FlowEngineException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DefaultCommandExecutorTest {

    @Test
    public void testExecuteSingleCommand() {
        DefaultCommandExecutor commandExecutor = createCommandExecutor();
        Command<String> commandA = commandContext -> "AAA";
        String res = commandExecutor.execute(commandA);
        assertThat(res).isEqualTo("AAA");
    }

    @Test
    public void testExecuteSingleCommand_throwException() {
        DefaultCommandExecutor commandExecutor = createCommandExecutor();
        Command<String> commandA = commandContext -> {
            throw new RuntimeException("CommandAException");
        };
        assertThatThrownBy(() -> {
            String res = commandExecutor.execute(commandA);
            System.out.println(res);
        }).isExactlyInstanceOf(FlowEngineException.class)
                .getCause()
                .hasMessageContaining("CommandAException");

    }


    @Test
    public void testExecuteNestedCommand() {
        DefaultCommandExecutor commandExecutor = createCommandExecutor();
        Command<String> commandA = commandContext -> "AAA";
        Command<String> commandB = commandContext -> {
            String strA = commandExecutor.execute(commandA);
            return strA + "BBB";
        };
        String res = commandExecutor.execute(commandB);
        assertThat(res).isEqualTo("AAABBB");
    }

    @Test
    public void testExecuteNestedCommand_throwException() {
        DefaultCommandExecutor commandExecutor = createCommandExecutor();
        Command<String> commandA = commandContext -> {
            throw new RuntimeException("CommandAException");
        };
        Command<String> commandB = commandContext -> {
            String strA = commandExecutor.execute(commandA);
            return strA + "BBB";
        };
        assertThatThrownBy(() -> {
            String res = commandExecutor.execute(commandB);
            System.out.println(res);
        }).isExactlyInstanceOf(FlowEngineException.class)
                .getCause()
                .hasMessageContaining("CommandAException");
    }

    @Test
    public void testExecuteNestedCommand_throwException2() {
        DefaultCommandExecutor commandExecutor = createCommandExecutor();
        Command<String> commandA = commandContext -> "AAA";
        Command<String> commandB = commandContext -> {
            String res = commandExecutor.execute(commandA);
            System.out.println(res);
            throw new RuntimeException("CommandBException");
        };
        assertThatThrownBy(() -> {
            String res = commandExecutor.execute(commandB);
            System.out.println(res);
        }).isExactlyInstanceOf(FlowEngineException.class)
                .getCause()
                .hasMessageContaining("CommandBException");
    }

    private DefaultCommandExecutor createCommandExecutor() {
        CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor();
        AbstractEngineConfiguration engineConfiguration = new AbstractEngineConfiguration() {
        };
        commandContextInterceptor.setEngineConfiguration(engineConfiguration);
        commandContextInterceptor.setCommandContextFactory(new CommandContextFactory(engineConfiguration));

        CommandInterceptor commandInvoker = new AbstractCommandInterceptor() {
            @Override
            public <T> T execute(Command<T> command, CommandExecutor commandExecutor) {
                CommandContext commandContext = Context.getCommandContext();
                return command.execute(commandContext);
            }
        };
        commandContextInterceptor.setNext(commandInvoker);
        return new DefaultCommandExecutor(commandContextInterceptor);
    }

}