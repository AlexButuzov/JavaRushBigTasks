package bankomatEmulator;

public enum Operation {
    LOGIN, INFO, DEPOSIT, WITHDRAW, EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i) {
        Operation operation = null;
        if (i == 0) throw new IllegalArgumentException();
        else if (i == 1) operation = INFO;
        else if (i == 2) operation = DEPOSIT;
        else if (i == 3) operation = WITHDRAW;
        else if (i == 4) operation = EXIT;
        else throw new IllegalArgumentException();

        return operation;
    }
}
