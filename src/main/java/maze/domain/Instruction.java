package maze.domain;

public enum Instruction {
    MOVE, LEFT, RIGHT, UTURN,
    HALF_LEFT, HALF_RIGHT;

    //assuming we may have additional directions
    // HALF_LEFT -> turn half left (45 degree instead of 90)
    // HALF_RIGHT -> turn half right (45 degree instead of 90)
    //Thus we can achieve 8 directions N, NE, E, SE, S, SW, W, NW

    public Instruction opposite() {
        return this == LEFT ? RIGHT : LEFT;
    }
}
