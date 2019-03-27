package googleGuavaAndApacheCollections;


import   googleGuavaAndApacheCollections.strategy.StorageStrategy;

/**
 * укорачиватель Shortener
 * паттерн Команда
 * внешние библиотеки коллкций goagle guava, appache commons-collections4
 * тест Junit4
 */

/*
Напишем укорачиватель Shortener. Это будет некий аналог укорачивателя
ссылок Google URL Shortener (https://goo.gl), но мы расширим его функциональность и
сделаем консольным. Он будет сокращать не только ссылки, но и любые строки.
Наш Shortener - это класс, который может для любой строки вернуть некий
уникальный идентификатор и наоборот, по ранее полученному идентификатору
вернуть строку.

Два дополнительных требования к Shortener:
- для двух одинаковых строк должен возвращаться один и тот же идентификатор;
- он должен поддерживать столько строк, сколько значений может принимать long,
именно этот тип будет использоваться для идентификатора.
Первое требование очень сильно влияет на производительность, т.к. при получении
идентификатора для новой строки мы должны проверить не обрабатывалась ли эта
строка ранее, чтобы вернуть старый идентификатор.
 */
public class Shortener {
    /*
    private Long lastId поле будет отвечать за последнее значение идентификатора,
    которое было использовано при добавлении новой строки в хранилище.
     */
    private Long lastId = 0L;

    private StorageStrategy storageStrategy; // здесь храниться стратегия хранения данных.

    public Shortener(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    /*
        будет возвращать идентификатор id для заданной строки.
         */
    public synchronized Long getId(String string) {
        if (storageStrategy.containsValue(string)) {
            return storageStrategy.getKey(string);
        } else {
            lastId++;
            storageStrategy.put(lastId,string);
            return storageStrategy.getKey(string);
        }

    }

    /*
    будет возвращать строку для заданного
    идентификатора или null, если передан неверный идентификатор.

     */
    public synchronized String getString(Long id) {
        /*if (storageStrategy.containsKey(id))   //не принял валидатор
            return storageStrategy.getValue(id);
        return null;*/

        return storageStrategy.getValue(id);
    }



}
