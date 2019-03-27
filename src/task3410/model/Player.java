package   task3410.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Player extends CollisionObject implements Movable {
    private Image image;
    public Player(int x, int y) {
        super(x, y);
        try {
            image = ImageIO.read(new File(imageAddress +"Player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(image,getX() - getWidth() / 2, getY() - getHeight() / 2,null);
        /*graphics.setColor(Color.GREEN);
        graphics.drawOval(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
        graphics.fillOval(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());*/

    }

    @Override
    public void move(int x, int y) {
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
    }
}
