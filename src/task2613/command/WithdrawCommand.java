package   task2613.command;

import   task2613.CashMachine;
import   task2613.ConsoleHelper;
import   task2613.CurrencyManipulator;
import   task2613.CurrencyManipulatorFactory;
import   task2613.exception.InterruptOperationException;
import   task2613.exception.NotEnoughMoneyException;


import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

class WithdrawCommand implements Command {
    private final Locale currentLocale = new Locale("en");
    private ResourceBundle res = ResourceBundle.getBundle(
            CashMachine.RESOURCE_PATH + "withdraw", currentLocale);

    @Override
    public void execute() throws InterruptOperationException {
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory
                .getManipulatorByCurrencyCode(currencyCode);
        ConsoleHelper.writeMessage(res.getString("before"));

        while (true) {

            ConsoleHelper.writeMessage(res.getString("specify.amount"));
            String countMoneyFromСonsole = ConsoleHelper.readString();

            if (countMoneyFromСonsole.replaceAll("[0-9]", "").length() == 0) {
                int expectedAmount = Integer.parseInt(countMoneyFromСonsole);
                if (currencyManipulator.isAmountAvailable(expectedAmount)) {
                    try {
                        Map<Integer, Integer> banknotsCountMap = currencyManipulator.withdrawAmount(expectedAmount);
                        ConsoleHelper.writeMessage(res.getString("success.format"));
                        banknotsCountMap.entrySet().forEach(entry -> {

                            ConsoleHelper.writeMessage("\t" + entry.getKey() + " - " + entry.getValue());
                        });
                        break;
                    } catch (NotEnoughMoneyException e) {
                        ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));

                    }
                } else ConsoleHelper.writeMessage(res.getString("not.enough.money"));
            }  else ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));


        }

    }
}
