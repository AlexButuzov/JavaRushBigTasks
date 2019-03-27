package   LogPareserAndSimpleQueue.query;

import   LogPareserAndSimpleQueue.Event;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public interface EventQuery {
    /**
     * Метод должен возвращать количество уникальных событий за выбранный период.
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return количесвто событий
     */
    int getNumberOfAllEvents(Date after, Date before);

    /**
     * Метод должен возвращать множество уникальных событий за выбранный период.
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множество событий
     */
    Set<Event> getAllEvents(Date after, Date before);

    /**
     * Метод должен возвращать множество уникальных событий ,
     * которые происходили с переданного IP адреса
     * за выбранный период.
     *
     * @param ip     IP
     * @param after  от
     * @param before до
     * @return множество событий
     */
    Set<Event> getEventsForIP(String ip, Date after, Date before);

    /**
     * Метод должен возвращать множество уникальных событий ,
     * которые произвел переданный пользователь
     * за выбранный период.
     *
     * @param user   юзер
     * @param after  от
     * @param before до
     * @return множество событий
     */
    Set<Event> getEventsForUser(String user, Date after, Date before);

    /**
     * Метод должен возвращать множество уникальных событий ,
     * у которых статус выполнения FAILED
     * за выбранный период.
     *
     * @param after  от
     * @param before до
     * @return множество событий
     */
    Set<Event> getFailedEvents(Date after, Date before);

    /**
     * Метод должен возвращать множество уникальных событий ,
     * у которых статус выполнения  ERROR
     * за выбранный период.
     *
     * @param after  от
     * @param before до
     * @return множество событий
     */
    Set<Event> getErrorEvents(Date after, Date before);

    /**
     * Метод должен возвращать количество попыток решить задачу
     * с номером task
     * за выбранный период.
     *
     * @param task   номер
     * @param after  от
     * @param before до
     * @return число попыток решить задачу
     */
    int getNumberOfAttemptToSolveTask(int task, Date after, Date before);

    /**
     * Метод должен возвращать количество успешных решений задачи
     * с номером task
     * за выбранный период.
     *
     * @param task   номер
     * @param after  от
     * @param before до
     * @return количество успешных решений задачи
     */
    int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before);

    /**
     * Метод должен возвращать мапу (номер_задачи : количество_попыток_решить_ее)
     * за выбранный период.
     *
     * @param after  от
     * @param before до
     * @return мапа (номер_задачи : количество_попыток_решить_ее)
     */
    Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before);

    /**
     * Метод должен возвращать мапу (номер_задачи : сколько_раз_ее_решили)
     * за выбранный период.
     *
     * @param after  от
     * @param before до
     * @return мапа (номер_задачи : сколько_раз_ее_решили)
     */
    Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before);
}