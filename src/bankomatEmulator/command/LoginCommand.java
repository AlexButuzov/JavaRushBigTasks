package bankomatEmulator.command;

import bankomatEmulator.CashMachine;
import bankomatEmulator.ConsoleHelper;
import bankomatEmulator.exception.InterruptOperationException;

import java.util.Locale;
import java.util.ResourceBundle;

public class LoginCommand implements Command {
    //    protected static final  String PIN = "1234";
//    protected static final  String CARD_ID = "123456789012";
    private final Locale currentLocale = new Locale("en");

    private ResourceBundle res = ResourceBundle.getBundle(
            CashMachine.RESOURCE_PATH + "login"
            , currentLocale);

    private final ResourceBundle validCreditCards = ResourceBundle.getBundle(
            CashMachine.class.getPackage().getName() + ".resources.verifiedCards");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));

        while (true) {
            ConsoleHelper.writeMessage(res.getString("specify.data"));
            String cardIdFromConsol = ConsoleHelper.readString();

            String pinFromConsol = ConsoleHelper.readString();
            if (!isaValidData(cardIdFromConsol, pinFromConsol)) {
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            } else if (!isVerified(cardIdFromConsol, pinFromConsol)) {
                ConsoleHelper.writeMessage(res.getString("not.verified.format"));
            } else {
                ConsoleHelper.writeMessage(res.getString("success.format"));
                break;
            }
            ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
        }
    }

    private boolean isVerified(String cardIdFromConsol, String pinFromConsol) {
        Boolean rezult = validCreditCards.containsKey(cardIdFromConsol)
                && validCreditCards.getObject(cardIdFromConsol).equals(pinFromConsol);

        return rezult;
//        return CARD_ID.equals(cardIdFromConsol)
//                && PIN.equals(pinFromConsol);
    }

    private boolean isaValidData(String cardIdFromConsol, String pinFromConsol) {
        Boolean rezult = cardIdFromConsol != null
                && cardIdFromConsol.length() == 12
                && cardIdFromConsol.replaceAll("[0-9]", "").length() == 0
                && pinFromConsol != null
                && pinFromConsol.length() == 4
                && pinFromConsol.replaceAll("[0-9]", "").length() == 0;

        return rezult;
    }
}
