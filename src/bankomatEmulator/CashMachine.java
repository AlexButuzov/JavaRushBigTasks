package bankomatEmulator;

import bankomatEmulator.command.CommandExecutor;
import bankomatEmulator.exception.InterruptOperationException;

public class CashMachine {

    private static String sourceAdress = (CashMachine.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1) +
            CashMachine.class.getPackage().getName().replaceAll(".model", "") + ".logs")
            .replaceAll("[.]", "/").replace("target/classes", "src");
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

        } catch (Exception e) {
            ConsoleHelper.printExitMessage();
        }

    }
}
