package   LogPareserAndSimpleQueue.query;

import   LogPareserAndSimpleQueue.Event;
import   LogPareserAndSimpleQueue.Status;

import java.util.Date;
import java.util.Set;

public interface IPQuery {
    /**
     * Метод должен возвращать количесвто уникальных IP за промежуток вермени
     *
     * @param after  от
     * @param before до
     * @return множество IP
     */
    int getNumberOfUniqueIPs(Date after, Date before);

    /**
     * Метод должен возвращать список уникальных IP за промежуток вермени
     *
     * @param after  от
     * @param before до
     * @return множество IP
     */
    Set<String> getUniqueIPs(Date after, Date before);

    /**
     * Метод должен возвращать список уникальных IP
     * с которых события сгенерированы заданным пользователем за заданный промежуток вермени.
     *
     * @param user   юзер
     * @param after  от
     * @param before до
     * @return множество IP
     */
    Set<String> getIPsForUser(String user, Date after, Date before);

    /**
     * Метод должен возвращать список уникальных IP,
     * событие с которых совпадает с заданным за заданный промежуток вермени,
     *
     * @param event  стобытие
     * @param after  от
     * @param before до
     * @return множество IP
     */
    Set<String> getIPsForEvent(Event event, Date after, Date before);

    /**
     * Метод должен возвращать список уникальных IP,
     * события с которых закончилось переданным статусом за заданный промежуток вермени,
     *
     * @param status статус
     * @param after  от
     * @param before до
     * @return множество IP
     */
    Set<String> getIPsForStatus(Status status, Date after, Date before);
}