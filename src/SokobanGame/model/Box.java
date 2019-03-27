package SokobanGame.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Box extends CollisionObject implements Movable {
    private Image image;

    public Box(int x, int y) {
        super(x, y);
        boxOnHome(false);
    }

    public void boxOnHome(boolean isBoxOnHome) {
        try {
            if (isBoxOnHome) image = ImageIO.read(new File(imageAddress +"BoxOnHome.png"));
            else image = ImageIO.read(new File(imageAddress +"BoxAlone.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(image,getX() - getWidth() / 2, getY() - getHeight() / 2,null);
        /*graphics.setColor(Color.ORANGE);
        graphics.drawRect(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
        graphics.fillRect(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());*/

    }

    @Override
    public void move(int x, int y) {
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
    }
}
