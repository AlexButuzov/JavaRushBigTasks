package com.javarush.task.task33.task3310.strategy;
/*
 * `Как ты заметил, получение идентификатора для строки требует намного больше времени,
 * чем получение строки по идентификатору. Это ожидаемо и следует из реализации HashMap.
 * Давай напишем четвертую стратегию OurHashBiMapStorageStrategy, которая будет лишена этого недостатка.
 * 11.1. Создай класс OurHashBiMapStorageStrategy, реализующий интерфейс StorageStrategy.
 * 11.2. Добавь в него два поля HashMap<Long, String> k2v и HashMap<String, Long> v2k.
 * Первое будет хранить соответствие ключа и значения, а второе наоборот: значения и ключа.
 * 11.3. Реализуй методы интерфейса StorageStrategy, обеспечив максимальную скорость.
 */

import java.util.HashMap;

public class OurHashBiMapStorageStrategy implements StorageStrategy {
    private HashMap<Long, String> k2v = new HashMap<>();
    private HashMap<String, Long> v2k = new HashMap<>();

    @Override
    public boolean containsKey(Long key) {
        return k2v.containsKey(key);
    }

    @Override
    public boolean containsValue(String value) {
        return v2k.containsKey(value);
    }

    @Override
    public void put(Long key, String value) {
        k2v.put(key, value);
        v2k.put(value, key);
    }

    @Override
    public Long getKey(String value) {
        return v2k.get(value);
    }

    @Override
    public String getValue(Long key) {
        return k2v.get(key);
    }
}
