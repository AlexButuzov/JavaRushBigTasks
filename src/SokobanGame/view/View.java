package   SokobanGame.view;

import   SokobanGame.controller.Controller;
import   SokobanGame.controller.EventListener;
import   SokobanGame.model.GameObjects;

import javax.swing.*;

public class View extends JFrame {
    private Controller controller;
    private Field field;


    public View(Controller controller) {
        this.controller = controller;
    }

    public void init() {
        field = new Field(this);
        add(field);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setTitle("Сокобан");
        setVisible(true);
    }

    public void setEventListener(EventListener eventListener) {
        field.setEventListener(eventListener);
    }

    /**
     * метод update() будет обновлять представление (перерисовывать поле)
     */
    public void update() {
        field.repaint();
    }

    /**
     * метод должен получать объекты у контроллера
     *
     * @return GameObjects - все игрровые объекты
     */
    public GameObjects getGameObjects() {
        return controller.getGameObjects();
    }

    public void completed(int level) {
        this.update();
        JOptionPane.showMessageDialog(field,"Level " + level + " Complited!!!");
        controller.startNextLevel();
    }

}
