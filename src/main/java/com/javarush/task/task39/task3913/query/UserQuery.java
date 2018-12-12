package com.javarush.task.task39.task3913.query;

import java.util.Date;
import java.util.Set;

public interface UserQuery {
    /**
     * Метод должен возвращать список уникальных пользователей
     *
     * @return множество пользователей
     */
    Set<String> getAllUsers();

    /**
     * Метод должен возвращать количество уникальных пользователей
     * за промежуток вермени
     *
     * @param after  от
     * @param before до
     * @return количество пользователей
     */
    int getNumberOfUsers(Date after, Date before);

    /**
     * Метод должен возвращать количесвто событий
     * сгенерировнных пользователем
     * за промежуток вермени
     *
     * @param after  от
     * @param before до
     * @return количестов событий
     */
    int getNumberOfUserEvents(String user, Date after, Date before);

    /**
     * Метод должен возвращать множество пользователей с определенным IP
     * Несколько пользователей могут использовать один и тот же IP.
     *
     * @param ip     IP
     * @param after  от
     * @param before до
     * @return множество пользователей
     */
    Set<String> getUsersForIP(String ip, Date after, Date before);

    /**
     * Метод должен возвращать множесто пользователей,
     * которые были залогинены
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множесто пользователей
     */
    Set<String> getLoggedUsers(Date after, Date before);

    /**
     * Метод должен возвращать множесто пользователей,
     * которые скачали плагин
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множесто пользователей
     */
    Set<String> getDownloadedPluginUsers(Date after, Date before);

    /**
     * Метод должен возвращать множесто пользователей,
     * которые отправили сообщение
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множесто пользователей
     */
    Set<String> getWroteMessageUsers(Date after, Date before);

    /**
     * Метод должен возвращать множесто пользователей,
     * которые решали любую задачу
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множесто пользователей
     */
    Set<String> getSolvedTaskUsers(Date after, Date before);

    /**
     * Метод должен возвращать множесто пользователей,
     * которые решали задачу с номером "task"
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @param task   номер задачи
     * @return множесто пользователей
     */
    Set<String> getSolvedTaskUsers(Date after, Date before, int task);

    /**
     * Метод должен возвращать множесто пользователей,
     * которые решили любую задачу
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множесто пользователей
     */
    Set<String> getDoneTaskUsers(Date after, Date before);

    /**
     * Метод должен возвращать множесто пользователей,
     * которые решили задачу с номером "task"
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @param task   номер задачи
     * @return множесто пользователей
     */
    Set<String> getDoneTaskUsers(Date after, Date before, int task);
}