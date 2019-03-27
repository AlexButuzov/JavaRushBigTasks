package LogPareserAndSimpleQueue;

import   LogPareserAndSimpleQueue.query.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author Alexey N.Butuzov
 * @date 06.11.2018
 */

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    private List<Path> logFiles = Collections.synchronizedList(new ArrayList<>());


    private LogParser() {
    }

    public LogParser(Path logDir) {
        if (Files.isDirectory(logDir)) {
            try {
                Files.list(logDir).forEach(
                        fileInLogDir -> {

                            if (fileInLogDir.toString().endsWith(".log")) {
                                logFiles.add(fileInLogDir);
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (logDir.toString().endsWith(".log")) logFiles.add(logDir);
    }

    // реализация интерфейса IPQuery

    /**
     * Метод должен возвращать количесвто уникальных IP за промежуток вермени
     *
     * @param after  от
     * @param before до
     * @return множество IP
     */
    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after, before).size();
    }

    /**
     * Метод должен возвращать список уникальных IP за промежуток вермени
     *
     * @param after  от
     * @param before до
     * @return множество IP
     */
    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        Set<String> uniqueIPs = new TreeSet<>();
        allLineInTime(after, before).forEach(line -> {
            uniqueIPs.add(getIpFromLine(line));
        });

        return uniqueIPs;
    }


    /**
     * Метод должен возвращать список уникальных IP
     * с которых события сгенерированы заданным пользователем за заданный промежуток вермени.
     *
     * @param user   юзер
     * @param after  от
     * @param before до
     * @return множество IP
     */
    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        Set<String> uniqueIPs = new TreeSet<>();
        allLineInTime(after, before).forEach(line -> {
            if (line.split("\t")[1].equals(user)) uniqueIPs.add(getIpFromLine(line));
        });
        return uniqueIPs;
    }

    /**
     * Метод должен возвращать список уникальных IP,
     * событие с которых совпадает с заданным за заданный промежуток вермени,
     *
     * @param event  стобытие
     * @param after  от
     * @param before до
     * @return множество IP
     */
    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        Set<String> uniqueIPs = new TreeSet<>();
        allLineInTime(after, before).forEach(line -> {
            if (line.contains(event.toString()))
                uniqueIPs.add(getIpFromLine(line));
        });
        return uniqueIPs;
    }

    /**
     * Метод должен возвращать список уникальных IP,
     * события с которых закончилось переданным статусом за заданный промежуток вермени,
     *
     * @param status статус
     * @param after  от
     * @param before до
     * @return множество IP
     */
    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        Set<String> uniqueIPs = new TreeSet<>();
        allLineInTime(after, before).forEach(line -> {
            if (getStatusFormLine(line).equals(status))
                uniqueIPs.add(getIpFromLine(line));
        });
        return uniqueIPs;
    }

    // реализация интерфейса UserQuery

    /**
     * Метод должен возвращать список уникальных пользователей
     *
     * @return множество пользователей
     */
    @Override
    public Set<String> getAllUsers() {
        Set<String> uniqueUsers = new TreeSet<>();
        for (Path logFile :
                logFiles) {
            try {
                Files.lines(logFile).forEach(line -> {
                    String currentUser =  line.split("\t")[1];
                    uniqueUsers.add(
                            currentUser);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return uniqueUsers;

    }

    /**
     * Метод должен возвращать количество уникальных пользователей
     * за промежуток вермени
     *
     * @param after  от
     * @param before до
     * @return количество пользователей
     */
    @Override
    public int getNumberOfUsers(Date after, Date before) {
        Set<String> uniqueUsers = new TreeSet<>();
        allLineInTime(after, before).forEach(line -> {
            String currentUser = line.split("\t")[1];
            uniqueUsers.add(
                    currentUser);
        });
        return uniqueUsers.size();

    }

    /**
     * Метод должен возвращать количесвто событий
     * сгенерировнных пользователем
     * за промежуток вермени
     *
     * @param after  от
     * @param before до
     * @return количестов событий
     */
    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        Set<String> uniqueUserEvents = new TreeSet<>();
        allLineInTime(after, before).forEach(line -> {
            String currentUser = line.split("\t")[1];
            if (currentUser.equals(user))
                uniqueUserEvents.add(line.split("\t")[3].split(" ")[0]);

        });
        return uniqueUserEvents.size();
    }

    /**
     * Метод должен возвращать множество пользователей с определенным IP
     * Несколько пользователей могут использовать один и тот же IP.
     *
     * @param ip     IP
     * @param after  от
     * @param before до
     * @return множество пользователей
     */
    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        Map<String, Boolean> uniqueUsersForIP = new TreeMap<>();
        allLineInTime(after, before).forEach(line -> {
            if (line.split("\t")[0].equals(ip)) {
                String currentUser = line.split("\t")[1];
                uniqueUsersForIP.put(currentUser, true);
            }
        });

        return uniqueUsersForIP.keySet();
    }

    /**
     * Метод должен возвращать множесто пользователей,
     * которые были залогинены
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множесто пользователей
     */
    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        Map<String, Boolean> uniqueLoggedUsers = new TreeMap<>();
        allLineInTime(after, before).forEach(line -> {
            if (line.contains(
                    Event.LOGIN.toString())) {
                String currentUser = line.split("\t")[1];
                uniqueLoggedUsers.put(currentUser, true);
            }
        });
        return uniqueLoggedUsers.keySet();
    }

    /**
     * Метод должен возвращать множесто пользователей,
     * которые скачали плагин
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множесто пользователей
     */
    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        Map<String, Boolean> uniqueDownloadedPluginUsers = new TreeMap<>();
        allLineInTime(after, before).forEach(line -> {
            if (line.contains(
                    Event.DOWNLOAD_PLUGIN.toString())) {
                String currentUser = line.split("\t")[1];
                uniqueDownloadedPluginUsers.put(currentUser, true);
            }
        });

        return uniqueDownloadedPluginUsers.keySet();
    }

    /**
     * Метод должен возвращать множесто пользователей,
     * которые отправили сообщение
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множесто пользователей
     */
    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        Map<String, Boolean> uniqueWroteMessageUsers = new TreeMap<>();
        allLineInTime(after, before).forEach(line -> {
            if (line.contains(
                    Event.WRITE_MESSAGE.toString())) {
                String currentUser = line.split("\t")[1];
                uniqueWroteMessageUsers.put(currentUser, true);
            }
        });
        return uniqueWroteMessageUsers.keySet();
    }

    /**
     * Метод должен возвращать множесто пользователей,
     * которые решали любую задачу
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множесто пользователей
     */
    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        Map<String, Boolean> uniqueSolvedTaskUsers = new TreeMap<>();
        allLineInTime(after, before).forEach(line -> {
            if (line.contains(
                    Event.SOLVE_TASK.toString())) {
                String currentUser = line.split("\t")[1];
                uniqueSolvedTaskUsers.put(currentUser, true);
            }
        });
        return uniqueSolvedTaskUsers.keySet();
    }

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
    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        Map<String, Boolean> uniqueSolvedTaskUsers = new TreeMap<>();
        allLineInTime(after, before).forEach(line -> {
            if (line.contains(
                    Event.SOLVE_TASK.toString())
                    && line.split("\t")[3].split(" ")[1]
                    .contains(((Integer) task).toString())) {
                String currentUser = line.split("\t")[1];
                uniqueSolvedTaskUsers.put(currentUser, true);
            }
        });

        return uniqueSolvedTaskUsers.keySet();
    }

    /**
     * Метод должен возвращать множесто пользователей,
     * которые решили любую задачу
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множесто пользователей
     */
    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        Map<String, Boolean> uniqueDoneTaskUsers = new TreeMap<>();
        allLineInTime(after, before).forEach(line -> {
            if (line.contains(
                    Event.DONE_TASK.toString())) {
                String currentUser = line.split("\t")[1];
                uniqueDoneTaskUsers.put(currentUser, true);
            }
        });
        return uniqueDoneTaskUsers.keySet();
    }

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
    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        Map<String, Boolean> uniqueDoneTaskUsers = new TreeMap<>();
        for (Path logFile :
                logFiles) {
            try {
                Files.lines(logFile).forEach(line -> {
                    if (line.contains(
                            Event.DONE_TASK.toString())
                            && line.split("\t")[3].split(" ")[1]
                            .contains(((Integer) task).toString())) {
                        Date dateInLine = getDateFromLogLine(line);
                        if (isEventAfterStart(dateInLine, after)
                                && isEventBeforeEnd(dateInLine, before)) {
                            String currentUser = line.split("\t")[1];
                            uniqueDoneTaskUsers.put(currentUser, true);
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return uniqueDoneTaskUsers.keySet();
    }

    // реализация интерфейса DateQuery

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
    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        Map<Date, Boolean> uniqueDatesForUserAndEvent = new TreeMap<>();
        allLineInTime(after, before).forEach(line -> {
            String currentUser = line.split("\t")[1];
            if (currentUser.equals(user)
                    && line.contains(event.toString()))
                uniqueDatesForUserAndEvent.put(getDateFromLogLine(line), true);
        });
        return uniqueDatesForUserAndEvent.keySet();
    }

    /**
     * Метод должен возвращать множесто дат,
     * когда когда любое событие не выполнилось (статус FAILED),
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множесто дат
     */
    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        Map<Date, Boolean> uniqueDatesWhenSomethingFailed = new TreeMap<>();
        allLineInTime(after, before).forEach(line -> {

            if (getStatusFormLine(line).equals(Status.FAILED))
                uniqueDatesWhenSomethingFailed.put(getDateFromLogLine(line), true);
        });
        return uniqueDatesWhenSomethingFailed.keySet();
    }

    /**
     * Метод должен возвращать множесто дат,
     * когда когда любое событие закончилось ошибкой (статус ERROR),
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множесто дат
     */
    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        Map<Date, Boolean> uniqueDatesWhenErrorHappened = new TreeMap<>();
        allLineInTime(after, before).forEach(line -> {

            if (getStatusFormLine(line).equals(Status.ERROR))
                uniqueDatesWhenErrorHappened.put(getDateFromLogLine(line), true);
        });
        return uniqueDatesWhenErrorHappened.keySet();
    }

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
    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        Map<Date, Boolean> dateWhenUserLogged = new TreeMap<>();
        final Date[] dateWhenUserLoggedFirstTime = {null};
        allLineInTime(after, before).forEach(line -> {
            String currentUser = line.split("\t")[1];
            if (user.contains(currentUser)
                    && line.contains(Event.LOGIN.toString()))
                if (dateWhenUserLoggedFirstTime[0] == null
                        || dateWhenUserLoggedFirstTime[0].after(getDateFromLogLine(line)))
                    dateWhenUserLoggedFirstTime[0] = getDateFromLogLine(line);
        });
        return dateWhenUserLoggedFirstTime[0];

    }

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
    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        final Date[] dateWhenUserSolvedTask = {null};
        String taskStatus = new StringBuilder()
                .append(Event.SOLVE_TASK)
                .append(" ")
                .append(task)
                .toString();

        allLineInTime(after, before).forEach(line -> {
            String currentUser = line.split("\t")[1];
            if (user.contains(currentUser)
                    && line.contains(taskStatus))
                if (dateWhenUserSolvedTask[0] == null ||
                        dateWhenUserSolvedTask[0].after(getDateFromLogLine(line)))
                    dateWhenUserSolvedTask[0] = getDateFromLogLine(line);
        });
        return dateWhenUserSolvedTask[0];
    }

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
    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        final Date[] dateWhenUserDoneTask = {null};
        String taskStatus = new StringBuilder()
                .append(Event.DONE_TASK)
                .append(" ")
                .append(task)
                .toString();

        allLineInTime(after, before).forEach(line -> {
            String currentUser = line.split("\t")[1];
            if (user.contains(currentUser)
                    && line.contains(taskStatus))
                if (dateWhenUserDoneTask[0] == null
                        || dateWhenUserDoneTask[0].after(getDateFromLogLine(line)))
                    dateWhenUserDoneTask[0] = getDateFromLogLine(line);
        });
        return dateWhenUserDoneTask[0];
    }

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
    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        Map<Date, Boolean> datesWhenUserWroteMessage = new TreeMap<>();
        String taskStatus = Event.WRITE_MESSAGE.toString();

        allLineInTime(after, before).forEach(line -> {
            String currentUser = line.split("\t")[1];
            if (user.contains(currentUser)
                    && line.contains(taskStatus))
                datesWhenUserWroteMessage.put(getDateFromLogLine(line), true);
        });
        return datesWhenUserWroteMessage.keySet();

    }

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
    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        Map<Date, Boolean> datesWhenUserDownloadedPlugin = new TreeMap<>();
        String taskStatus = Event.DOWNLOAD_PLUGIN.toString();

        allLineInTime(after, before).forEach(line -> {
            String currentUser = line.split("\t")[1];
            if (user.contains(currentUser)
                    && line.contains(taskStatus))
                datesWhenUserDownloadedPlugin.put(getDateFromLogLine(line), true);
        });
        return datesWhenUserDownloadedPlugin.keySet();
    }

    /**
     * Метод должен возвращать количество уникальных событий за выбранный период.
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return количесвто событий
     */
    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    /**
     * Метод должен возвращать множество уникальных событий за выбранный период.
     * за промежуток времени.
     *
     * @param after  от
     * @param before до
     * @return множество событий
     */
    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        Map<Event, Boolean> eventsAllInThisTimeDateIntarval = new TreeMap<>();

        allLineInTime(after, before).forEach(line ->
                eventsAllInThisTimeDateIntarval.put(getEventFromLine(line), true)
        );

        return eventsAllInThisTimeDateIntarval.keySet();
    }

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
    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        Map<Event, Boolean> eventsForIP = new TreeMap<>();

        allLineInTime(after, before).forEach(line -> {
                    if (ip.equals(
                            getIpFromLine(line)
                    ))
                        eventsForIP.put(getEventFromLine(line), true);
                }
        );

        return eventsForIP.keySet();
    }

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
    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {

        Map<Event, Boolean> eventsForUser = new TreeMap<>();

        allLineInTime(after, before).forEach(line -> {
                    if (line.contains(user))
                        eventsForUser.put(getEventFromLine(line), true);
                }
        );

        return eventsForUser.keySet();
    }

    /**
     * Метод должен возвращать множество уникальных событий ,
     * у которых статус выполнения FAILED
     * за выбранный период.
     *
     * @param after  от
     * @param before до
     * @return множество событий
     */
    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {

        Map<Event, Boolean> eventsFailed = new TreeMap<>();

        allLineInTime(after, before).forEach(line -> {
                    if (getStatusFormLine(line).equals(Status.FAILED))
                        eventsFailed.put(getEventFromLine(line), true);
                }
        );
        return eventsFailed.keySet();
    }

    /**
     * Метод должен возвращать множество уникальных событий ,
     * у которых статус выполнения  ERROR
     * за выбранный период.
     *
     * @param after  от
     * @param before до
     * @return множество событий
     */
    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {

        Map<Event, Boolean> eventsError = new TreeMap<>();

        allLineInTime(after, before).forEach(line -> {
                    if (getStatusFormLine(line).equals(Status.ERROR))
                        eventsError.put(getEventFromLine(line), true);
                }
        );
        return eventsError.keySet();

    }

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
    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        final int[] counter = new int[1];

        allLineInTime(after, before).forEach(line -> {
                    String taskStatus = new StringBuilder()
                            .append(Event.SOLVE_TASK)
                            .append(" ")
                            .append(task)
                            .toString();
                    if (line.contains(taskStatus))
                        counter[0]++;
                }
        );
        return counter[0];
    }

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
    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        final int[] counter = new int[1];
        counter[0] = 0;

        allLineInTime(after, before).forEach(line -> {
                    String taskStatus = new StringBuilder()
                            .append(Event.DONE_TASK)
                            .append(" ")
                            .append(task)
                            .toString();
                    if (line.contains(taskStatus))
                        counter[0]++;
                }
        );
        return counter[0];

    }

    /**
     * Метод должен возвращать мапу (номер_задачи : количество_попыток_решить_ее)
     * за выбранный период.
     *
     * @param after  от
     * @param before до
     * @return мапа (номер_задачи : количество_попыток_решить_ее)
     */
    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> rezult = new TreeMap<>();

        allLineInTime(after, before).stream()
                .filter(line -> line.contains(Event.SOLVE_TASK.toString())).forEach(line -> {
                    Integer currentTask = getTaskFromLine(line);
                    if (!rezult.containsKey(currentTask))
                        rezult.put(currentTask, getNumberOfAttemptToSolveTask(currentTask, after, before));
                }
        );

        return rezult;
    }


    /**
     * Метод должен возвращать мапу (номер_задачи : сколько_раз_ее_решили)
     * за выбранный период.
     *
     * @param after  от
     * @param before до
     * @return мапа (номер_задачи : сколько_раз_ее_решили)
     */
    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> rezult = new TreeMap<>();


        allLineInTime(after, before).stream()
                .filter(line -> line.contains(Event.DONE_TASK.toString())).forEach(line -> {
                    Integer currentTask = getTaskFromLine(line);
                    if (!rezult.containsKey(currentTask))
                        rezult.put(currentTask, getNumberOfSuccessfulAttemptToSolveTask(currentTask, after, before));
                }
        );

        return rezult;
    }


    /**
     * Метод пока должен поддерживать только следующие запросы(Условие 5-7) :
     * 5.1.1. get ip
     * 5.1.2. get user
     * 5.1.3. get date
     * 5.1.4. get event
     * 5.1.5. get status
     *
     * @param query входные параметры
     * @return множестов в завсимости от входный параметров.
     */
    @Override
    public Set<Object> execute(String query) {
        Set<Object> rezultOfExecuteQuery = new HashSet<>();
        Date after = null, before = null;
        if (query.contains("and date between")) {
            after = getDateFromLine(query.split("and date between")[1].split("\"")[1]);
            before = getDateFromLine(query.split("and date between")[1].split("\"")[3]);
        }

        allLineInTime(after, before).stream().
                filter(line -> isExpectedLine(line, query)). //поиск строк с заданным параметром
                forEach(line ->
                rezultOfExecuteQuery.add(rezultQueryInCurrentLine(line, query)));   //поиск в строке элементов запрашиваемых как результат
        return rezultOfExecuteQuery;
    }

    private Object rezultQueryInCurrentLine(String line, String query) {
        switch (query.split(" ")[1]) {
            case "user":
                return line.split("\t")[1];
            case "ip":
                return line.split("\t")[0];
            case "event":
                return getEventFromLine(line);
            case "date":
                return getDateFromLogLine(line);
            case "status":
                return getStatusFormLine(line);
            default:
                return null;
        }
    }

    private boolean isExpectedLine(String line, String query) {
        if (query.contains("\"")) {
            Boolean checkRezult = true;
            String[] queryByArray = query.split(" = \"");
            for (int i = 1; i < queryByArray.length; i++) {
                if (!line.contains(queryByArray[i].split("\"")[0])) checkRezult = false;
            }
            if (!checkForName(query, line)) checkRezult = false;
            return checkRezult;

        } else return true;

    }

    //проверяем включает ли строка имя, если имя идет пармаетром запроса
    private boolean checkForName(String query, String line) {
        if (query.split("for")[1].contains("user"))
            return line.split("\t")[1].equals(
                    query.split("user = \"")[1].split("\"")[0]);
        else return true;
    }

    //получаем событие из строки лога
    private Event getEventFromLine(String line) {
        String eventInLine = line.split("\t")[3].split(" ")[0];
        for (Event event : Event.values()) {
            if (event.toString().equals(
                    eventInLine))
                return event;
        }
        return null;
    }

    //получаем номер задачи из строки лога
    private Integer getTaskFromLine(String line) {
        if (getEventFromLine(line).equals(Event.DONE_TASK)
                || getEventFromLine(line).equals(Event.SOLVE_TASK))
            return Integer.parseInt(line.split("\t")[3].split(" ")[1]);
        return null;
    }

    //получаем статус из строки лога
    private Status getStatusFormLine(String line) {
        String eventInLine = line.split("\t")[4];
        for (Status status : Status.values()) {
            if (status.toString().equals(
                    eventInLine))
                return status;
        }
        return null;
    }


    //получаем ip из строки лога
    private String getIpFromLine(String line) {
        return line.trim().split("\t")[0];
    }

    //получаем дату из строки лога
    private Date getDateFromLogLine(String line) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyyy HH:mm:ss");
        try {
            return format.parse(line.split("\t")[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //получаем дату из строки запроса
    private Date getDateFromLine(String line) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyyy HH:mm:ss");
        try {
            return format.parse(line);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    //проверяет произошло ли событие  в требуемый промежуток вермени
    private boolean isEventBeforeEnd(Date dateInLine, Date before) {
        boolean isEventBeforeEnd = false;
        if (before == null) isEventBeforeEnd = true;
        else if (dateInLine.before(before))// || dateInLine.equals(before))
            isEventBeforeEnd = true;
        return isEventBeforeEnd;
    }

    private boolean isEventAfterStart(Date dateInLine, Date after) {
        boolean isEventAfterStart = false;
        if (after == null) isEventAfterStart = true;
        else if (dateInLine.after(after))//|| dateInLine.equals(after))
            isEventAfterStart = true;
        return isEventAfterStart;
    }

    //метод составлейт множестов всех срок лога ,
    // добавленных в определенный промежуток времени
    private Set<String> allLineInTime(Date after, Date before) {
        Set<String> allLineInTime = new HashSet<>();
        for (Path logFile :
                logFiles) {
            try {
                Files.lines(logFile).forEach(line -> {
                    Date dateInLine = getDateFromLogLine(line);
                    if (isEventAfterStart(dateInLine, after)
                            && isEventBeforeEnd(dateInLine, before)) {

                        allLineInTime.add(line);
                    }

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return allLineInTime;
    }
}