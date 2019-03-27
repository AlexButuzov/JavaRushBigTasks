package   SokobanGame.model;

import java.util.HashSet;
import java.util.Set;

public class GameObjects {
    private Set<Wall> walls;
    private Set<Box> boxes;
    private Set<Home> homes;
    private Player player;

    public Set<Wall> getWalls() {
        return walls;
    }

    public Set<Box> getBoxes() {
        return boxes;
    }

    public Set<Home> getHomes() {
        return homes;
    }

    public Player getPlayer() {
        return player;
    }

    public GameObjects(Set<Wall> walls, Set<Box> boxes, Set<Home> homes, Player player) {
        this.walls = walls;
        this.boxes = boxes;
        this.homes = homes;
        this.player = player;
    }

    public Set<GameObject> getAll() {
        HashSet<GameObject> rezultAllGameObject = new HashSet<>();
        rezultAllGameObject.addAll(walls);
        rezultAllGameObject.addAll(boxes);
        rezultAllGameObject.addAll(homes);
        rezultAllGameObject.add(player);
        return rezultAllGameObject;
    }
}
