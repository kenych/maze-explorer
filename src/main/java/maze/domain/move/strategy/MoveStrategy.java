package maze.domain.move.strategy;

import maze.domain.Position;

@FunctionalInterface
public interface MoveStrategy {
    Position getNextPositionFor(Position position);

}
