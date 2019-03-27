package   SokobanGame.model;

import   SokobanGame.controller.EventListener;

import java.nio.file.Paths;

public class Model {

    public static int FIELD_CELL_SIZE = 20; //размер ячейки игрового поля

    private EventListener eventListener;

    private GameObjects gameObjects;

    private int currentLevel = 1;

    /*private LevelLoader levelLoader = new LevelLoader(
            new File(this.getClass().getPackage().getName()
                    + ".res.levels.txt").toPath());
*/
    private LevelLoader levelLoader = new LevelLoader(Paths.get(
            (Model.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1) +
                    Model.class.getPackage().getName().replaceAll(".model", "") + ".res.")
                    .replaceAll("[.]", "/").replace("4/JavaCollections", "4.JavaCollections")
                    + "levels.txt"));

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restartLevel(int level) {
        this.gameObjects = levelLoader.getLevel(level);
    }

    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        currentLevel++;
        restart();
    }


    /**
     * Метод поддерживает движение бъекта Player в направлениии Direction
     * Метод должен:
     * Проверить столкновение со стеной (метод checkWallCollision()), если есть столкновение - выйти из метода.
     * Проверить столкновение с ящиками (метод checkBoxCollisionAndMoveIfAvaliable()),
     * если есть столкновение - выйти из метода.
     * Передвинуть игрока в направлении direction.
     * Проверить завершен ли уровень.
     *
     * @param direction
     */
    public void move(Direction direction) {
        Player player = gameObjects.getPlayer();
        final boolean[] rezult = {true};
        if (checkWallCollision(player, direction)) return;
        if (checkBoxCollisionAndMoveIfAvaliable(direction)) return;

        movePlayer(player, direction);
        checkBoxesOnHomes();
        checkCompletion();
    }


    /**
     * метод проверяет столкновение со стеной. Он должен вернуть true,
     * если при движении объекта gameObject в направлении direction произойдет столкновение со стеной, иначе false.
     * Для определения столкновения используй метод isCollision() у игрового объекта.
     *
     * @param gameObject
     * @param direction
     * @return
     */
    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {

        for (Wall wall : gameObjects.getWalls()) {
            if (gameObject.isCollision(wall, direction)) return true;
        }
        return false;
    }

    /**
     * метод проверяет столкновение с ящиками
     * Метод должен:
     * Вернуть true, если игрок не может быть сдвинут в направлении direction
     * (там находится: или ящик, за которым стена; или ящик за которым еще один ящик).
     * Вернуть false, если игрок может быть сдвинут в направлении direction
     * (там находится: или свободная ячейка; или дом; или ящик, за которым свободная ячейка или дом).
     * При этом, если на пути есть ящик, который может быть сдвинут,
     * то необходимо переместить этот ящик на новые координаты. Обрати внимание,
     * что все объекты перемещаются на фиксированное значение FIELD_CELL_SIZE,
     * независящее от размеров объекта, которые используются для его отрисовки.
     *
     * @param direction
     * @return
     */
    public boolean checkBoxCollisionAndMoveIfAvaliable(Direction direction) {
        Player player = gameObjects.getPlayer();
        final boolean[] rezult = {false};

        gameObjects.getBoxes().forEach(box -> {
            if (player.isCollision(box, direction)) {
                rezult[0] = checkBoxCollision(box, direction);
                if (!rezult[0]) moveBox(box, direction);
            }
        });

        return rezult[0];
    }

    /**
     * метод вигает объект box на один шаг, заданный в FIELD_CELL_SIZE
     * TODO отдельный абстарктный класс MovableCollisionObject extends Collision implements Movable
     * TODO наследовать от эого класса Box и Player и преобразовать в moveMovable(MovableCollisionObject , Direction)
     * TODO Т.к. сейчас надо создавать отдельные методы move(Direction) для каждого класса Box и Player
     *
     * @param box
     * @param direction
     */
    public void moveBox(Box box, Direction direction) {
        switch (direction) {
            case UP:
                box.move(0, -FIELD_CELL_SIZE);
                break;
            case DOWN:
                box.move(0, +FIELD_CELL_SIZE);
                break;
            case LEFT:
                box.move(-FIELD_CELL_SIZE, 0);
                break;
            case RIGHT:
                box.move(+FIELD_CELL_SIZE, 0);
                break;
        }
    }

    /**
     * метод вигает объект box на один шаг, заданный в FIELD_CELL_SIZE
     * TODO отдельный абстарктный класс MovableCollisionObject extends Collision implements Movable
     * TODO наследовать от эого класса Box и Player
     * TODO Т.к. сейчас надо создавать отдельные методы move(Direction) для каждого класса Box и Player
     *
     * @param player
     * @param direction
     */

    protected void movePlayer(Player player, Direction direction) {
        switch (direction) {
            case UP:
                player.move(0, -FIELD_CELL_SIZE);
                break;
            case DOWN:
                player.move(0, +FIELD_CELL_SIZE);
                break;
            case LEFT:
                player.move(-FIELD_CELL_SIZE, 0);
                break;
            case RIGHT:
                player.move(+FIELD_CELL_SIZE, 0);
                break;
        }
    }


    /**
     * метод проверяет столкновение ящика с ящиками и стенами
     *
     * @param box
     * @param direction
     * @return
     */
    public boolean checkBoxCollision(Box box, Direction direction) {
        final boolean[] rezult = {false};
        if (checkWallCollision(box, direction)) rezult[0] = true;
        else {
            gameObjects.getBoxes().forEach(boxOnField -> {
                if (box.isCollision(boxOnField, direction))
                    rezult[0] = true;
            });
        }
        return rezult[0];
    }

    /**
     * Метод должен проверить пройден ли уровень
     * (на всех ли домах стоят ящики, их координаты должны совпадать).
     * Если условие выполнено, то проинформировать слушателя событий,
     * что текущий уровень завершен.
     */
    public void checkCompletion() {
       /* final boolean[] isDone = {true};
        Set<Box> boxes = gameObjects.getBoxes();
        gameObjects.getHomes().forEach(home -> {
            final boolean[] noBoxOnHome = {true};
            boxes.forEach(box -> {
                if (home.getX() == box.getX()
                        && home.getY() == box.getY()) noBoxOnHome[0] = false;
            });
            if (noBoxOnHome[0]) isDone[0] = false;
        });

        if (isDone[0]) eventListener.levelCompleted(currentLevel);
*/
        int numberCollisions = 0;
        for (Box coBox : gameObjects.getBoxes()) {
            for (GameObject goHome : gameObjects.getHomes()) {
                if ((coBox.getX() == goHome.getX())
                        && (coBox.getY() == goHome.getY())) {
                    numberCollisions++;
                }
            }
        }
        if (numberCollisions == gameObjects.getBoxes().size()) {
            eventListener.levelCompleted(currentLevel);
        }
    }

    private void checkBoxesOnHomes() {
        for (Box coBox : gameObjects.getBoxes()) {
            boolean isBoxOnHome = false;
            for (GameObject goHome : gameObjects.getHomes()) {
                if ((coBox.getX() == goHome.getX())
                        && (coBox.getY() == goHome.getY())) {
                    isBoxOnHome = true;
                }
                coBox.boxOnHome(isBoxOnHome);
            }
        }
    }
}