package googleGuavaAndApacheCollections;

import   googleGuavaAndApacheCollections.strategy.*;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * укорачиватель Shortener
 * паттерн Команда
 * внешние библиотеки коллкций goagle guava, appache commons-collections4
 * тест Junit4
 *
 * @author Alexey N.Butuzov
 * @date 31.08.2018
 */
public class Solution {
    public static void main(String[] args) {

        /*использвание java.util.HashMap*/
        testStrategy(new HashMapStorageStrategy(), 10000);
        /*использовани самописного специализированного аналога HashMap, вида HashMap<Long,String>*/
        testStrategy(new OurHashMapStorageStrategy(), 10000);
        /* использовани самописного специализированного аналога HashMap, c FileBucket в качестве ведер (англ. bucket)*/
        testStrategy(new FileStorageStrategy(), 40);
        /*Использование в связке двух коллеций */
        testStrategy(new OurHashBiMapStorageStrategy(), 10000);

        /*рассморим реализацию HashBiMap из сторонней библиотеки коллекций Guava от Google.*/
        testStrategy(new HashBiMapStorageStrategy(), 10000);

        /*Рассмотрим еще одну реализацию BiMap, на этот раз из Apache Commons Collections.*/
        testStrategy(new DualHashBidiMapStorageStrategy(), 10000);
    }

    /*
    Set<Long> getIds(Shortener shortener, Set<String> strings).
    Этот метод должен для переданного множества строк возвращать множество идентификаторов.
    Идентификатор для каждой отдельной строки нужно получить, используя shortener
     */
    private static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> rezult = new HashSet<>();
        strings.forEach(line -> rezult.add(shortener.getId(line)));
        return rezult;
    }

    /*
    Set<String> getStrings(Shortener shortener, Set<Long> keys).
    Метод будет возвращать множество строк, которое соответствует
    переданному множеству идентификаторов.
     */
    private static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> rezult = new HashSet<>();
        keys.forEach(key -> rezult.add(shortener.getString(key)));
        return rezult;
    }

    /*
     * testStrategy(StorageStrategy strategy, long elementsNumber). Метод будет тестировать работу
     * переданной стратегии на определенном количестве элементов elementsNumber.
     * Реализация метода должна:
     * 6.2.3.1. Выводить имя класса стратегии. Имя не должно включать имя пакета.
     * 6.2.3.2. Генерировать тестовое множество строк, используя Helper и заданное количество элементов elementsNumber.
     * 6.2.3.3. Создавать объект типа Shortener, используя переданную стратегию.
     * 6.2.3.4. Замерять и выводить время необходимое для отработки метода getIds для заданной стратегии и заданного множества элементов. Время вывести в миллисекундах. При замере времени работы метода можно пренебречь переключением процессора на другие потоки, временем, которое тратится на сам вызов, возврат значений и вызов методов получения времени (даты). Замер времени произведи с использованием объектов типа Date.
     * 6.2.3.5. Замерять и выводить время необходимое для отработки метода getStrings для заданной стратегии и полученного в предыдущем пункте множества идентификаторов.
     * 6.2.3.6. Сравнивать одинаковое ли содержимое множества строк, которое было сгенерировано и множества,
     * которое было возвращено методом getStrings.
     * Если множества одинаковы, то выведи "Тест пройден.", иначе "Тест не пройден.".
     * 6.2.4. Добавь метод main(). Внутри метода протестируй стратегию HashMapStorageStrategy с помощью 10000 элементов.
     */
    private static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        Shortener shortener;
        Set<String> testSet = new HashSet<>();
        Helper.printMessage(strategy.getClass().getSimpleName());
        for (long i = 0; i < elementsNumber; i++) {
            testSet.add(Helper.generateRandomString());
        }
        shortener = new Shortener(strategy);

        Date currentTime = new Date();
        long startTime = currentTime.getTime(); // System.nanoTime(); не принял валидатор
        Set<Long> ids = getIds(shortener, testSet);
        Date currentTime1 = new Date();
        long stopTime = currentTime1.getTime(); // System.nanoTime(); не принял валидатор
        Helper.printMessage(Long.toString(stopTime - startTime));


        Date currentTime2 = new Date();
        long startTime2 = currentTime2.getTime(); // System.nanoTime(); не принял валидатор
        Set<String> stringSet = getStrings(shortener, ids);

        Date currentTime3 = new Date();
        long stopTime2 = currentTime3.getTime(); // System.nanoTime(); не принял валидатор
        Helper.printMessage(Long.toString(stopTime2 - startTime2));

        Helper.printMessage(
                testSet.size() == stringSet.size() ?
                        "Тест пройден." : "Тест не пройден."
        );
    }
}
