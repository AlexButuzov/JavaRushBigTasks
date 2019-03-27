package   LogPareserAndSimpleQueue.query;

import java.util.Set;

public interface QLQuery {

    /**
     * Метод пока должен поддерживать только следующие запросы(Условие 5 - Условаие 8) :
     * 5.1.1. get ip
     * 5.1.2. get user
     * 5.1.3. get date
     * 5.1.4. get event
     * 5.1.5. get status
     *
     * @param query входные параметры
     * @return множестов в завсимости от входный параметров.
     */
    Set<Object> execute(String query);
}