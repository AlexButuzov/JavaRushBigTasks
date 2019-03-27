package   task2613.command;

import   task2613.CashMachine;
import   task2613.ConsoleHelper;
import   task2613.CurrencyManipulator;
import   task2613.CurrencyManipulatorFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;


class InfoCommand implements Command {
    private final Locale currentLocale = new Locale("en");

    private ResourceBundle res = ResourceBundle.getBundle(
            CashMachine.RESOURCE_PATH + "info"
            ,currentLocale);
    @Override
    public void execute() {
        Collection<CurrencyManipulator> manipulators = CurrencyManipulatorFactory.getAllCurrencyManipulators();
        if (manipulators != null) manipulators.forEach(manipulator -> {
            ConsoleHelper.writeMessage(res.getString("before"));
            if (manipulator.hasMoney()) {
                System.out.print(manipulator.getCurrencyCode());
                System.out.print(" - ");
                System.out.println(manipulator.getTotalAmount());
            } else {
                ConsoleHelper.writeMessage(res.getString("no.money"));
            }
        });
        else ConsoleHelper.writeMessage(res.getString("no.money"));

    }
}
