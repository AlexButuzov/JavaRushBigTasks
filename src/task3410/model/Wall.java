package   task3410.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Wall extends CollisionObject {
    private Image image;

    public Wall(int x, int y) {
        super(x, y);
        try {
            image = ImageIO.read(new File(imageAddress +"Wall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(image,getX() - getWidth() / 2, getY() - getHeight() / 2,null);
        /*graphics.setColor(Color.gray);
        graphics.drawRect(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
        graphics.fillRect(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());*/
    }
}
