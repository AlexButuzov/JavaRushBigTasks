package   SokobanGame.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

/**
 * тестовая реализация загрузчика уровней LevelLoader.
 */
public class LevelLoader {
    private Path levels;   //путь к тестовому файлу, содержащему описание уровней

    public LevelLoader(Path levels) {
        this.levels = levels;
    }



    public GameObjects getLevel(int level) {
        if (level > 60) level %= 60; // В игре 60 уровней - изменить при добавлении редактора
        StringBuffer bufferedLevel = readLevelFromFileToBuffer(level);
        GameObjects gameObjectsFromBuffer =
                parseBufferToGameObjects(bufferedLevel);
        return gameObjectsFromBuffer;
    }

    /**
     * Метод парсит буфер, в котороом хранится информация о строении уровня
     * Символ 'X' - означает стену, '*' - ящик, '.' - дом, '&' - ящик который стоит в доме, а '@' - игрока
     *
     * @param bufferedLevel
     * @return
     */
    private GameObjects parseBufferToGameObjects(StringBuffer bufferedLevel) {

        HashSet<Wall> walls = new HashSet<>();
        HashSet<Box> boxes = new HashSet<>();
        HashSet<Home> homes = new HashSet<>();
        Player player = null;
        int xCoordinate = 0, yCoordinate = 0;

        for (int i = 0; i < bufferedLevel.length(); i++) {
            Character curentChar = bufferedLevel.charAt(i);
            Pair pair = new Pair(xCoordinate, yCoordinate);
            xCoordinate++;
            switch (curentChar) {
                case '\n':
                    yCoordinate++;
                    xCoordinate = 0;
                    break;
                case 'X':
                    walls.add(new Wall(pair.getX(), pair.getY()));
                    break;
                case '*':
                    boxes.add(new Box(pair.getX(), pair.getY()));
                    break;
                case '.':
                    homes.add(new Home(pair.getX(), pair.getY()));
                    break;
                case '&':
                    Box box = new Box(pair.getX(), pair.getY());
                    box.boxOnHome(true);
                    boxes.add(box);
                    homes.add(new Home(pair.getX(), pair.getY()));
                    break;
                case '@':
                    player = new Player(pair.getX(), pair.getY());
                    break;
            }
        }
        return new GameObjects(
                walls, boxes, homes, player);
    }

    private StringBuffer readLevelFromFileToBuffer(int level) {
        StringBuffer rezult = new StringBuffer();
        try
                (BufferedReader bufferedReader = Files.newBufferedReader(levels);) {
            if (bufferedReader.ready()) {
                String currentLine = bufferedReader.readLine();
                String levelToLoad = "Maze:" + level;
                while (!currentLine.replaceAll(" ","").contains(levelToLoad)) currentLine = bufferedReader.readLine();
                for (int i = 0; i < 6; i++) {
                    currentLine = bufferedReader.readLine();
                }
                currentLine = bufferedReader.readLine();
                while (currentLine.replaceAll(" ", "").length() != 0) {
                    rezult.append(currentLine).append("\n");
                    currentLine = bufferedReader.readLine();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rezult;
    }

    private class Pair {
        private int x, y;
        private int stepOfCell = Model.FIELD_CELL_SIZE;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        Pair(int x, int y) {
            this.x = x * stepOfCell + stepOfCell / 2;
            this.y = y * stepOfCell + stepOfCell / 2;
        }
    }
}
