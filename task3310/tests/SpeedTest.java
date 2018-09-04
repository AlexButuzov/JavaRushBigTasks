package com.javarush.task.task33.task3310.tests;
/*
 * Напишем еще один тест, который проверит, что получить идентификатор для строки используя стратегию
 * HashBiMapStorageStrategy можно быстрее, чем используя стратегию HashMapStorageStrategy.
 * 15.1. Создай класс SpeedTest в пакете tests.
 * 15.2. Добавь в класс метод long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids).
 * Он должен возвращать время в миллисекундах необходимое для получения идентификаторов для всех строк из strings.
 * Идентификаторы должны быть записаны в ids.
 * 15.3. Добавь в класс метод long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings).
 * Он должен возвращать время в миллисекундах необходимое для получения строк для всех строк из ids.
 * Строки должны быть записаны в strings.
 * 15.4. Добавь в класс SpeedTest тест testHashMapStorage(). Он должен:
 * 15.4.1. Создавать два объекта типа Shortener, один на базе HashMapStorageStrategy, второй на базе HashBiMapStorageStrategy. Н
 * азовем их shortener1 и shortener2.
 * 15.4.2. Генерировать с помощью Helper 10000 строк и помещать их в сет со строками, назовем его origStrings.
 * 15.4.3. Получать время получения идентификаторов для origStrings (вызывать метод getTimeForGettingIds для shortener1,
 * а затем для shortener2).
 * 15.4.4. Проверять с помощью junit, что время, полученное в предыдущем пункте для shortener1 больше, чем для shortener2.
 * 15.4.5. Получать время получения строк (вызывать метод getTimeForGettingStrings для shortener1 и shortener2).
 * 15.4.6. Проверять с помощью junit, что время, полученное в предыдущем пункте для shortener1
 * примерно равно времени для shortener2. Используй метод assertEquals(float expected,
 * float actual, float delta). В качестве delta можно использовать 30, этого вполне достаточно для наших экспериментов.
 */

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest extends Assert {
    public long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids) {

        Long startTime = new Date().getTime();
        strings.forEach(str -> ids.add(shortener.getId(str)));
        Long endTime = new Date().getTime();
        return endTime - startTime;
    }

    public long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        Long startTime = System.nanoTime()/ 1000;
        ids.forEach(id -> strings.add(shortener.getString(id)));
        Long endTime = System.nanoTime()/ 1000;
        return endTime - startTime;
    }

    @Test
    public void testHashMapStorage() {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());
        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            origStrings.add(Helper.generateRandomString());
        }
        Set<Long> ids = new HashSet<>();
        Long timeWhithHashMapIds= getTimeForGettingIds(shortener1, origStrings, ids);
        Long timeWhithHashMapStrings = getTimeForGettingStrings(shortener1,ids,new HashSet<String>());

        Set<Long> idsBi = new HashSet<>();
        Long timeWhithHashBiMapIds = getTimeForGettingIds(shortener2, origStrings,idsBi);
        Long timeWhithHashBiMapStrings = getTimeForGettingStrings(shortener2,idsBi,new HashSet<String>());


        Assert.assertTrue(timeWhithHashMapIds > timeWhithHashBiMapIds);
        Assert.assertEquals(timeWhithHashMapStrings, timeWhithHashBiMapStrings,30);
    }
}
