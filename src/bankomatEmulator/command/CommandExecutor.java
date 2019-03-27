package   bankomatEmulator.command;

import   bankomatEmulator.Operation;
import   bankomatEmulator.exception.InterruptOperationException;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
    private static final Map<Operation, Command> allKnownCommandsMap = new HashMap<>();

    static {
        allKnownCommandsMap.put(Operation.WITHDRAW, new WithdrawCommand());
        allKnownCommandsMap.put(Operation.INFO, new InfoCommand());
        allKnownCommandsMap.put(Operation.DEPOSIT, new DepositCommand());
        allKnownCommandsMap.put(Operation.EXIT, new ExitCommand());
        allKnownCommandsMap.put(Operation.LOGIN, new LoginCommand());

    }

    public static final void execute(Operation operation) throws InterruptOperationException {
        allKnownCommandsMap.get(operation).execute();
    }

    private CommandExecutor() {
    }
}
