package   googleGuavaAndApacheCollections.strategy;

import com.google.common.collect.HashBiMap;

/*
 * Такие коллекции уже реализованы в различных сторонних библиотеках коллекций. Одна из таких Guava от Google.
 * Задача, когда требуется создать Map, работающий в две стороны (по ключу получать значение, а по значению ключ)
 * не такая уж и редкая. Такие коллекции уже реализованы в различных сторонних библиотеках коллекций.
 * Одна из таких Guava от Google.
 * 12.1. Скачай и подключи библиотеку guava версии 19.0.
 * 12.2. Реализуй стратегию HashBiMapStorageStrategy. Она должна:
 * 12.2.1. Поддерживать интерфейс StorageStrategy.
 * 12.2.2. Внутри иметь только одно поле data типа HashBiMap.
 * 12.3. Проверь новую стратегию в методе main(). Запусти программу и сравни скорость работы пяти стратегий.
 */

public class HashBiMapStorageStrategy implements StorageStrategy {
    private HashBiMap<Long, String> data = HashBiMap.create();

    @Override
    public boolean containsKey(Long key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(String value) {
        return data.containsValue(value);
    }

    @Override
    public void put(Long key, String value) {
        data.put(key, value);
    }

    @Override
    public Long getKey(String value) {

        return data.inverse().get(value);
    }

    @Override
    public String getValue(Long key) {
        return data.get(key);
    }
}
