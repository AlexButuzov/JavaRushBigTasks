package   task2613;

import   task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConsoleHelper {
    private static Locale currentLocale = new Locale("en");
    private static ResourceBundle res = ResourceBundle.getBundle(
            CashMachine.RESOURCE_PATH + "common", currentLocale);
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws  InterruptOperationException {
        String stringFromConsol = null;
        try {
            stringFromConsol = bis.readLine();
        } catch (Exception exp) {
        } /* Согласно условия2
         п.1.2
         Если возникнет какое-то исключение при работе с консолью, то перехватим его и не будем обрабатывать.
        */
        String exitChekerString = stringFromConsol.toLowerCase();
        if (exitChekerString.contains("exit")) throw new InterruptOperationException();
        return stringFromConsol;
    }

    /**
     * Метод запрашивает код валюты, состоящий из трех символов
     *
     * @return Строка из трех символов, обозначающая код валюты
     */
    public static String askCurrencyCode() throws InterruptOperationException {
        String currencyCode = null;
        ConsoleHelper.writeMessage(res.getString("choose.currency.code"));
        while (currencyCode == null
                || currencyCode.length() != 3
                || currencyCode.replaceAll("[A-Z]", "").length() != 0) {
            currencyCode = readString().toUpperCase();
            if (currencyCode.length() != 3
                    || currencyCode.replaceAll("[A-Z]", "").length() != 0)
                ConsoleHelper.writeMessage(res.getString("invalid.data"));
        }
        return currencyCode;
    }


    /**
     * Этот метод должен предлагать пользователю ввести два целых положительных числа.
     * Первое число - номинал, второе - количество банкнот.
     * Никаких валидаторов на номинал нет. Т.е. 1200 - это нормальный номинал.
     * Если данные некорректны, то сообщить об этом пользователю и повторить.
     *
     * @param currencyCode код валюты из тре симфолов
     * @return массив строк из кода валюты, номинала и количесва купюр одного номинала.
     */
    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        String[] rezult = new String[3];

        ConsoleHelper.writeMessage(res.getString("choose.denomination.and.count.format"));
        while (rezult.length != 2) {
            String[] bufferRezult = readString().trim().split(" ");
            if (bufferRezult != null
                    && bufferRezult.length == 2
                    && bufferRezult[0].replaceAll("[0-9]", "").length() == 0
                    && bufferRezult[1].replaceAll("[0-9]", "").length() == 0
                    && Long.parseLong(bufferRezult[0]) > 0
                    && Integer.parseInt(bufferRezult[1]) > 0
                    )
                rezult = new String[]{bufferRezult[0], bufferRezult[1]};

            else {
                ConsoleHelper.writeMessage(res.getString("invalid.data"));
                ConsoleHelper.writeMessage(res.getString("choose.denomination.and.count.format"));
            }

        }
        return rezult;
    }

    public static Operation askOperation() throws InterruptOperationException {
        Operation operation = null;
        while (operation == null) {
            ConsoleHelper.writeMessage(res.getString("choose.operation"));
            ConsoleHelper.writeMessage(" 1 - " + res.getString("operation.INFO") +
                    "\n 2 - " + res.getString("operation.DEPOSIT") +
                    "\n 3 - " + res.getString("operation.WITHDRAW") +
                    "\n 4 - " + res.getString("operation.EXIT"));
            try {
                operation = Operation.getAllowableOperationByOrdinal(Integer.parseInt(readString()));
            } catch (IllegalArgumentException exp) {
                ConsoleHelper.writeMessage(res.getString("invalid.data"));
            }
        }
        return operation;

    }

    public static void printExitMessage() { ConsoleHelper.writeMessage(res.getString("the.end"));}
}
