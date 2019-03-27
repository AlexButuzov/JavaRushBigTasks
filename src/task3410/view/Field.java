package   task3410.view;

import   task3410.controller.EventListener;
import   task3410.model.Direction;
import   task3410.model.GameObjects;
import   task3410.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Field extends JPanel {
    private View view;
    private EventListener eventListener;


    public Field(View view) {
        this.view = view;
        KeyHandler keyHandler = new KeyHandler();
        this.addKeyListener(keyHandler); //установка слушателя клавиатуры
        this.setFocusable(true); //установка фокуса
    }

    public void paint(Graphics graphicField) {
        graphicField.setColor(Color.BLACK);
        graphicField.fillRect(0, 0, 500, 500);
        GameObjects gameObjects = view.getGameObjects();
        gameObjects.getAll().forEach(gameObject -> gameObject.draw(graphicField));
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * класс-обработчик нажатия клавиш клавиатуры
     */
    public class KeyHandler extends KeyAdapter {
        public KeyHandler() {
        }


        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    eventListener.move(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    eventListener.move(Direction.RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    eventListener.move(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    eventListener.move(Direction.DOWN);
                    break;
                case KeyEvent.VK_R:
                    eventListener.restart();
                    break;
            }
        }
    }
}
