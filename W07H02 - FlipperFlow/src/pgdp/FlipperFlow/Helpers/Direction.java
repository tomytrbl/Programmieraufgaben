package pgdp.FlipperFlow.Helpers;

public enum Direction {
    UP(0,1),
    DOWN(0,-1),
    LEFT(-1,0),
    RIGHT(1,0);


    Direction(int x, int y){
        vector = new Vector2(x, y);
    }
    private final Vector2 vector;
    public Vector2 getVector(){
        return vector;
    }

    public static Direction reverse(Direction direction){
        Direction ret = null;
        if (direction == UP){
            ret = DOWN;
        } else if (direction == DOWN) {
            ret = UP;
        } else if (direction == LEFT) {
            ret = RIGHT;
        } else if (direction == RIGHT) {
            ret = LEFT;
        }
        return ret;
    }

    public static Vector2 getCoordinatesFromDirection(Direction dir, Vector2 pos){
        return pos.add(dir.getVector());
    }

    public static Direction getDirectionFromCoordinates(Vector2 currentCoordinates, Vector2 nextCoordinates) {
        if (!(currentCoordinates.getX() == nextCoordinates.getX() || currentCoordinates.getY() == nextCoordinates.getY())) {
            return null;
        }
        if (Math.max(currentCoordinates.getY(), nextCoordinates.getY()) - Math.min(currentCoordinates.getY(), nextCoordinates.getY()) > 1 ||
                Math.max(currentCoordinates.getX(), nextCoordinates.getX()) - Math.min(currentCoordinates.getX(), nextCoordinates.getX()) > 1){
            return null;
        }
        if (currentCoordinates.getX() == nextCoordinates.getX() && currentCoordinates.getY() == nextCoordinates.getY()) {
            return null;
        }
        int retX = nextCoordinates.getX() - currentCoordinates.getX();
        int retY = nextCoordinates.getY() - currentCoordinates.getY();
        Direction ret = null;
        if (retX == 0 && retY == 1) {
            ret = UP;
        } else if (retX == 0 && retY == -1) {
            ret = DOWN;
        } else if (retX == 1 && retY == 0) {
            ret = RIGHT;
        } else if (retX == -1 && retY == 0) {
            ret = LEFT;
        }
        return ret;

    }
}
