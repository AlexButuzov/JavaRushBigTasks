package   task3913.query;

import   task3913.Event;

import java.util.Date;
import java.util.Set;

public interface DateQuery {
    /**
     * Метод должен возвращать множесто дат,
     * когда определенный пользователь произвел определенное событие.
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @param user   юзер
     * @param event  событие
     * @return множесто дат
     */
    Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before);

    /**
     * Метод должен возвращать множесто дат,
     * когда когда любое событие не выполнилось (статус FAILED),
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множесто дат
     */
    Set<Date> getDatesWhenSomethingFailed(Date after, Date before);

    /**
     * Метод должен возвращать множесто дат,
     * когда когда любое событие закончилось ошибкой (статус ERROR),
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множесто дат
     */
    Set<Date> getDatesWhenErrorHappened(Date after, Date before);

    /**
     * Метод должен возвращать дату,
     * когда когда пользователь залогинился впервые за указанный период. Если такой даты в логах нет - null.,
     * за промежуток времени.
     *
     * @param user   юзер
     * @param after  от
     * @param before до
     * @return дата
     */
    Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before);

    /**
     * Метод должен возвращать дату,
     * когда пользователь впервые попытался решить определенную задачу. Если такой даты в логах нет - null.
     * за промежуток времени.
     *
     * @param user   юзер
     * @param task   номер задачи
     * @param after  от
     * @param before до
     * @return дата
     */
    Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before);

    /**
     * Метод должен возвращать дату,
     * когда пользователь впервые решил определенную задачу. Если такой даты в логах нет - null.
     * за промежуток времени.
     *
     * @param user   юзер
     * @param task   номер задачи
     * @param after  от
     * @param before до
     * @return дата
     */
    Date getDateWhenUserDoneTask(String user, int task, Date after, Date before);

    /**
     * Метод должен возвращать множество дат,
     * когда пользователь написал сообщение.
     * за промежуток времени.
     *
     * @param user   юзер
     * @param after  от
     * @param before до
     * @return дата
     */
    Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before);

    /**
     * Метод должен возвращать множество дат,
     * когда пользователь скачал плагин.
     * за промежуток времени.
     *
     * @param user   юзер
     * @param after  от
     * @param before до
     * @return дата
     */
    Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before);
}