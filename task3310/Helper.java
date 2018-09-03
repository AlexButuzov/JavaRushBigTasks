package com.javarush.task.task33.task3310;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Helper {

    /*
    метод String generateRandomString() будет генерировать случайную строку.
    Воспользуйся для этого классами SecureRandom и BigInteger.
     */
    public static String generateRandomString() {

        return new BigInteger(130, new SecureRandom()).toString(36);
    }

    /*
     метод printMessage(String message) должен выводить переданный текст в консоль.
     Весь дальнейший вывод в программе должен быть реализован через этот метод!
     */
    public static void printMessage(String message) {
        System.out.println(message);
    }
}
