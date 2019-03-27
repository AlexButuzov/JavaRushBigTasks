package   googleGuavaAndApacheCollections.strategy;
/*
 * Рассмотрим еще одну реализацию BiMap, на этот раз из Apache Commons Collections.
 * 13.1. Скачай и подключи Apache Commons Collections 4.0.
 * 13.2. Реализуй стратегию DualHashBidiMapStorageStrategy. Она должна:
 * 13.2.1. Поддерживать интерфейс StorageStrategy.
 * 13.2.2. Внутри иметь только одно поле data с типом DualHashBidiMap.
 * 13.3. Проверь новую стратегию в методе main(). Запусти программу и сравни скорость работы шести стратегий.
 */

import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public class DualHashBidiMapStorageStrategy implements StorageStrategy {

    //private BidiMap<K,V>  data = new DualHashBidiMap();
    private DualHashBidiMap<Long,String> data = new DualHashBidiMap();

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
        data.put(key,value);
    }

    @Override
    public Long getKey(String value) {
        return  data.getKey(value);
    }

    @Override
    public String getValue(Long key) {

        return data.get(key);
    }
}


