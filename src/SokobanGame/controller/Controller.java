package SokobanGame.controller;

import SokobanGame.model.Direction;
import SokobanGame.model.GameObjects;
import SokobanGame.model.Model;
import SokobanGame.view.View;


public class Controller implements SokobanGame.controller.EventListener {
    private View view;
    private Model model;

    public Controller() {
        view = new View(this);
        model = new Model();

        model.restart();
        view.init();

        model.setEventListener(this);
        view.setEventListener(this);
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
    }

    @Override
    public void move(Direction direction) {
        model.move(direction);
        view.update();
    }

    @Override
    public void restart() {
        model.restart();
        view.update();
    }

    @Override
    public void startNextLevel() {
        model.startNextLevel();
        view.update();
    }

    @Override
    public void levelCompleted(int level) {
        view.completed(level);
    }

    /**
     * метод должен получать объекты у модели
     *
     * @return
     */
    public GameObjects getGameObjects() {
        return model.getGameObjects();
    }
}
