package bankomatEmulator;

import   bankomatEmulator.exception.NotEnoughMoneyException;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class CurrencyManipulator {
    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public String getCurrencyCode() {
        return currencyCode;
    }

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void addAmount(int denomination, int count) {
        denominations.put(denomination,
                denominations.getOrDefault(denomination, 0) + count);

    }

    /**
     * метод возвращает общую суммку банкнот в банкомате
     *
     * @return сумма
     */
    public int getTotalAmount() {
        AtomicReference<Integer> totalCount = new AtomicReference<>(0);
        denominations.entrySet().forEach(entry ->
                totalCount.set(totalCount.get() + entry.getKey() * entry.getValue()));
        return totalCount.get();
    }

    public boolean hasMoney() {
        return !denominations.isEmpty();
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return (expectedAmount <= getTotalAmount());
    }


    /**
     * Метод withdrawAmount должен возвращать минимальное количество банкнот, которыми набирается запрашиваемая сумма.
     * Используйте Жадный алгоритм
     *
     * @param expectedAmount ожидаемая сумма для выдачи
     * @return Map<Integer   ,       Integer> = HashMap<номинал, количество>
     * @throws NotEnoughMoneyException - если в банкомате недостоточно купюр для выдачи заданной  суммы
     */
    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {

        HashMap<Integer, Integer> rezult = new HashMap<>();
        ArrayList<Integer> keysList = new ArrayList<>(denominations.keySet());
        Collections.sort(keysList);
        Collections.reverse(keysList);
        keysList.stream().forEach(key -> {

        });
        for (int i = 0; i < keysList.size(); i++) {
            int currentBanknotValue = keysList.get(i);
            if (expectedAmount != 0 && (expectedAmount / currentBanknotValue) > 0) {
                if ((expectedAmount / currentBanknotValue) >= denominations.get(currentBanknotValue)) {
                    rezult.put(currentBanknotValue, denominations.get(currentBanknotValue));
                    expectedAmount -= currentBanknotValue * denominations.get(currentBanknotValue);
                    denominations.remove(currentBanknotValue);
                } else {
                    rezult.put(currentBanknotValue, (expectedAmount / currentBanknotValue));
                    denominations.put(currentBanknotValue, denominations.get(currentBanknotValue) - (expectedAmount / currentBanknotValue));
                    expectedAmount %= currentBanknotValue;
                }
            }
        }

        if (expectedAmount != 0) throw new NotEnoughMoneyException();
        return rezult;
    }


}
