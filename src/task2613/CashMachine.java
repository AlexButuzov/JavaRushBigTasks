package   task2613;

import   task2613.command.CommandExecutor;
import   task2613.exception.InterruptOperationException;

public class CashMachine {
    public static final String RESOURCE_PATH = CashMachine.class.getPackage().getName() + ".resources.";
    public static void main(String[] args) {

        Operation operation = null;
        try {

            CommandExecutor.execute(Operation.LOGIN);
            while (operation != Operation.EXIT) {
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            }
        } catch (InterruptOperationException | IllegalArgumentException e) {
            ConsoleHelper.printExitMessage();

        }

    }
}
