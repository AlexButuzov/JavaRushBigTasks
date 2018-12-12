package com.javarush.task.Paterns.Singletone.SingletoneVisEnum;

public enum SingletoneEnum {
    INSTANCE;
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
