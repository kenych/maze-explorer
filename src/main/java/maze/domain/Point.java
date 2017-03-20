package maze.domain;

import maze.domain.turn.Direction;

public class Point {
    private Position position;
    private Direction direction;

    public Point(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (position != null ? !position.equals(point.position) : point.position != null)
            return false;
        return direction == point.direction;

    }

    @Override
    public int hashCode() {
        int result = position != null ? position.hashCode() : 0;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "position=" + position +
                ", direction=" + direction +
                '}';
    }
}
