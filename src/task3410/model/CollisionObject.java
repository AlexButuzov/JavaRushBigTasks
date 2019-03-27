package task3410.model;


public abstract class CollisionObject extends GameObject {
    protected String imageAddress = (Box.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1) +
            Box.class.getPackage().getName().replaceAll(".model","") + ".res.images.")
            .replaceAll("[.]", "/").replace("4/JavaCollections", "4.JavaCollections");

    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction) {
        if (direction == Direction.LEFT)
            return super.getX() - Model.FIELD_CELL_SIZE == gameObject.getX()
                    && super.getY() == gameObject.getY();
        else if (direction == Direction.RIGHT)
            return super.getX() + Model.FIELD_CELL_SIZE == gameObject.getX()
                    && super.getY() == gameObject.getY();
        else if (direction == Direction.UP)
            return super.getY() - Model.FIELD_CELL_SIZE == gameObject.getY()
                    && super.getX() == gameObject.getX();
        else if (direction == Direction.DOWN)
            return super.getY() + Model.FIELD_CELL_SIZE == gameObject.getY()
                    && super.getX() == gameObject.getX();
        return false;
    }
}
