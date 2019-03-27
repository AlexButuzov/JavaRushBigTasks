package   task3310.strategy;
/**
 * Напишем еще одну стратегию, назовем ее FileStorageStrategy. Она будет очень похожа
 * на стратегию OurHashMapStorageStrategy, но в качестве ведер (англ. buckets) будут файлы.
 * Я знаю, ты знаешь о каких buckets идет речь, если нет - повтори внутреннее устройство HashMap.
 * 9.1. Создай класс FileBucket в пакете strategy.
 * 9.2. Добавь в класс поле Path path. Это будет путь к файлу.
 * 9.3. Добавь в класс конструктор без параметров, он должен:
 * 9.3.1. Инициализировать path временным файлом. Файл должен быть размещен в директории для временных файлов и иметь случайное имя.
 *
 * Подсказка: Files.createTempFile.
 *
 * 9.3.2. Создавать новый файл, используя path. Если такой файл уже есть, то заменять его.
 * 9.3.3. Обеспечивать удаление файла при выходе из программы.
 *
 * Подсказка: deleteOnExit().
 *
 * 9.4. Добавь в класс методы:
 * 9.4.1. long getFileSize(), он должен возвращать размер файла на который указывает path.
 * 9.4.2. void putEntry(Entry entry) - должен сериализовывать переданный entry в файл. Учти, каждый entry может содержать еще один entry.
 * 9.4.3. Entry getEntry() - должен забирать entry из файла. Если файл имеет нулевой размер, вернуть null.
 * 9.4.4. void remove() - удалять файл на который указывает path.
 * Конструктор и методы не должны кидать исключения.
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {
    private Path path;

    public FileBucket() {

        Path path = null;
        try {
            path = Files.createTempFile(null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.path = path;

        this.path.toFile().deleteOnExit();

    }

    public long getFileSize() {
        try {
            return Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void putEntry(Entry entry) {

        ObjectOutputStream objectOutputStream = null;
        try {

            objectOutputStream = new ObjectOutputStream(
                    Files.newOutputStream(path));
            objectOutputStream.writeObject(entry);
            // if (entry.next != null) putEntry(entry.next);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Entry getEntry() {
        if (this.getFileSize() > 0L) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path));
                Object object = objectInputStream.readObject();
                objectInputStream.close();
                return (Entry) object;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void remove() {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
