package   task3310.strategy;

public interface StorageStrategy {
    /*
    boolean containsKey(Long key) - должен вернуть true, если хранилище
    содержит переданный ключ.
     */
    boolean containsKey(Long key);

    /*
    boolean containsValue(String value) - должен вернуть true, если хранилище
    содержит переданное значение.
     */
    boolean containsValue(String value);

    /*void put(Long key, String value) - добавить в хранилище новую пару ключ -
    значение.
     */
    void put(Long key, String value);

    /*
    Long getKey(String value) - возвращает ключ для переданного значения.
     */
    Long getKey(String value);

    /*
    String getValue(Long key) - возвращает значение для переданного ключа.
     */
    String getValue(Long key);
}
