package   task2810.model;

import   task2810.vo.Vacancy;

import java.util.List;

/**
 * class Provider хранит ссылку на объект конкретной стратегии,
 * работая с этим объектом через общий интерфейс стратегий.
 * class Provider  выступает в качестве контекста Паттерна Стратегия
 * В данном случае имя интерфкейса Strategy
 *
 * В провайдере должен быть метод, который будет вызывать метод стратегии для выполнения главной операции.
 * Этот метод будет возвращать все java вакансии с выбранного сайта (ресурса).
 */

public class Provider {
    private Strategy strategy;

    public Provider(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<Vacancy> getJavaVacancies(String searchString) {
        return strategy.getVacancies(searchString);}
}
