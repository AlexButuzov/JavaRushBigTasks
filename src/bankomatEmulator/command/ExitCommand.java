package bankomatEmulator.command;

import bankomatEmulator.CashMachine;
import bankomatEmulator.ConsoleHelper;
import bankomatEmulator.exception.InterruptOperationException;

import java.util.Locale;
import java.util.ResourceBundle;

class ExitCommand implements Command {
    private final Locale currentLocale = new Locale("en");
    private ResourceBundle res = ResourceBundle.getBundle(
            CashMachine.RESOURCE_PATH + "exit"
            , currentLocale);

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("exit.question.y.n"));
        String qoueryOnExit = ConsoleHelper.readString().toLowerCase().trim();
        if ("y".equals(qoueryOnExit)) ConsoleHelper.writeMessage(res.getString("thank.message"));
    }
}
