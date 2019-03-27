package   SokobanGame.controller;

import   SokobanGame.model.Direction;

/**
 * интерфейс слушателя событий EventListener.
 * Его должен реализовывать каждый класс, который хочет обрабатывать события.
 * А классы, которые будут генерировать события, будут вызывать методы этого интерфейса.
 */

public interface EventListener {

    //передвинуть объект в определенном направлениии @Direction  по полю @Field
    void move(Direction direction);

    //начать заново текущий уровень
    void restart();

    //начать следующий уровень
    void startNextLevel();

    //уровень с номером level завершён
    void levelCompleted(int level);
}
