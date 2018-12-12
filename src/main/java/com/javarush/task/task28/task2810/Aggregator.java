package com.javarush.task.task28.task2810;

import com.javarush.task.task28.task2810.model.*;
import com.javarush.task.task28.task2810.view.HtmlView;
import com.javarush.task.task28.task2810.view.View;

/**
 * агрегатор java вакансий
 * Должен быть список сайтов, на которых мы ищем вакансии.
 * Для начала возьмем http://hh.ua/ и http://hh.ru/, потом уже добавим другие сайты поиска работы.
 * Это один и тот же сайт, только в разных доменах.
 * <p>
 * Используем Паттерн Стратегия
 *
 * @author Alexey N.Butuzov
 * @date 02.12.2018
 */

public class Aggregator {
    public static void main(String[] args) {
        HtmlView view = new HtmlView();
        Model model = new Model(view,
                new Provider(new HHStrategy()),
                new Provider(new MoikrugStrategy()));
        Controller controller = new Controller(model);
        view.setController(controller);
        ((HtmlView) view).userCitySelectEmulationMethod("Краснодар");
    }
}
