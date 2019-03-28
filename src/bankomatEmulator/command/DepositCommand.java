package bankomatEmulator.command;

import bankomatEmulator.CashMachine;
import bankomatEmulator.ConsoleHelper;
import bankomatEmulator.CurrencyManipulator;
import bankomatEmulator.CurrencyManipulatorFactory;
import bankomatEmulator.exception.InterruptOperationException;

import java.util.Locale;
import java.util.ResourceBundle;

class DepositCommand implements Command {
    private final Locale currentLocale = new Locale("en");
    private ResourceBundle res = ResourceBundle.getBundle(
            CashMachine.RESOURCE_PATH + "deposit"
            , currentLocale);

    @Override
    public void execute() throws InterruptOperationException {
        String currencyCode = ConsoleHelper.askCurrencyCode();
        ConsoleHelper.writeMessage(res.getString("before"));
        ConsoleHelper.writeMessage(res.getString("invalid.data"));
        String[] twoDigitsOfCurrencyCode = ConsoleHelper.getValidTwoDigits(currencyCode);
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        currencyManipulator.addAmount(
                Integer.parseInt(twoDigitsOfCurrencyCode[0]),
                Integer.parseInt(twoDigitsOfCurrencyCode[1]));
        ConsoleHelper.writeMessage(res.getString("success.format") + twoDigitsOfCurrencyCode[0] + twoDigitsOfCurrencyCode[1]);
    }
}
