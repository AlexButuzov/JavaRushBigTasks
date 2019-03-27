package   googleGuavaAndApacheCollections.strategy;
/**
 * Создай и реализуй класс FileStorageStrategy. Он должен:
 * 10.1. Реализовывать интерфейс StorageStrategy.
 * 10.2. Использовать FileBucket в качестве ведер (англ. bucket).
 *
 * Подсказка: класс должен содержать поле FileBucket[] table.
 *
 * 10.3. Работать аналогично тому, как это делает OurHashMapStorageStrategy,
 * но удваивать количество ведер не когда количество элементов size станет больше какого-то порога,
 * когда размер одного из ведер (файлов) стал больше bucketSizeLimit.
 * 10.3.1. Добавь в класс поле long bucketSizeLimit.
 * 10.3.2. Проинициализируй его значением по умолчанию, например, 10000 байт.
 * 10.3.3. Добавь сеттер и геттер для этого поля.
 * 10.4. При реализации метода resize(int newCapacity) проследи, чтобы уже не нужные файлы
 * были удалены (вызови метод remove()).
 * Проверь новую стратегию в методе main(). Учти, что стратегия FileStorageStrategy
 * гораздо более медленная, чем остальные. Не используй большое количество элементов для теста,
 * это может занять оооочень много времени.
 * Запусти программу и сравни скорость работы всех 3х стратегий.
 *
 * P.S. Обрати внимание на наличие всех необходимых полей в классе FileStorageStrategy,
 * по аналогии с OurHashMapStorageStrategy:
 * static final int DEFAULT_INITIAL_CAPACITY
 * static final long DEFAULT_BUCKET_SIZE_LIMIT
 * FileBucket[] table
 * int size
 * private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT
 * long maxBucketSize
 */


public class FileStorageStrategy implements StorageStrategy {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000L;
    FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    long maxBucketSize;



    public int hash(Long k) {
        k ^= (k >>> 20) ^ (k >>> 12);
        return (int) (k ^ (k >>> 7) ^ (k >>> 4));
    }

    public int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    public Entry getEntry(Long key) {
        int hash = (key == null) ? 0 : hash(key);

        for (Entry e = table[indexFor(hash, table.length)].getEntry(); e != null; e = e.next) {

            Long k;

            if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                return e;
        }

        return null;
    }

    public void resize(int newCapacity) {
        FileBucket[] newTable = new FileBucket[newCapacity];
        for (FileBucket fileBucket : newTable)
            fileBucket = new FileBucket();

        transfer(newTable);
        table = newTable;
    }

    public void transfer(FileBucket[] newTable) {
        int newCapacity = newTable.length;

        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            Entry entry = table[i].getEntry();

            while (entry != null) {
                Entry next = entry.next;
                int indexFor = indexFor(entry.hash, newCapacity);
                entry.next = newTable[indexFor].getEntry();
                newTable[indexFor].putEntry(entry);
                entry = next;
            }
            table[i].remove();
            table[i] = null;
        }
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry entry = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, entry));
        if (table[bucketIndex].getFileSize() >= bucketSizeLimit)
            resize(2 * table.length);
        size++;
    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        table[bucketIndex] = new FileBucket();
        table[bucketIndex].putEntry(new Entry(hash, key, value, null));
        size++;
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            for (Entry e = table[i].getEntry(); e != null; e = e.next)
                if (value.equals(e.value))
                    return true;
        }
        return false;
    }

    @Override
    public void put(Long key, String value) {
        int hash = hash(key);
        int i = indexFor(hash, table.length);

        if (table[i] == null)
            createEntry(hash, key, value, i);
        else {
            for (Entry entry = table[i].getEntry(); entry != null; entry = entry.next) {
                Long k;
                if (entry.hash == hash && ((k = entry.key) == key || key.equals(k)))
                    entry.value = value;
            }
            addEntry(hash, key, value, i);
        }
    }

    @Override
    public Long getKey(String value) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            for (Entry e = table[i].getEntry(); e != null; e = e.next)
                if (value.equals(e.value))
                    return e.key;
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        return getEntry(key).getValue();
    }

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getMaxBucketSize() {
        return maxBucketSize;
    }

    public void setMaxBucketSize(long maxBucketSize) {
        this.maxBucketSize = maxBucketSize;
    }
}
