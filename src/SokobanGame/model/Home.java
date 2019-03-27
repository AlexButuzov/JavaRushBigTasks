package   SokobanGame.model;

import java.awt.*;

public class Home extends GameObject {

    public Home(int x, int y) {
        super(x, y, 2, 2);
    }


    @Override
    public void draw(Graphics graphics) {

        graphics.setColor(Color.GREEN);
        graphics.drawOval(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
        graphics.fillOval(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());

    }
}
