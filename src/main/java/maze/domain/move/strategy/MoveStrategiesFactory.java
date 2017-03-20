package maze.domain.move.strategy;

import java.util.HashMap;
import java.util.Map;

import maze.domain.Position;
import maze.domain.turn.Direction;

public class MoveStrategiesFactory {
    private static Map<Direction, MoveStrategy> moveStrategyMap = new HashMap<>();

    static {
        moveStrategyMap.put(Direction.N, p -> new Position(p.getX(), p.getY() + 1));
        moveStrategyMap.put(Direction.S, p -> new Position(p.getX(), p.getY() - 1));
        moveStrategyMap.put(Direction.W, p -> new Position(p.getX() - 1, p.getY()));
        moveStrategyMap.put(Direction.E, p -> new Position(p.getX() + 1, p.getY()));

        //example of extending moving to NW
//        moveStrategyMap.put(Direction.NW, p -> new Position(p.getX() - 1, p.getY() + 1));
    }

    public static MoveStrategy strategyBy(Direction direction) {
        MoveStrategy moveStrategy = moveStrategyMap.get(direction);
        if (moveStrategy == null) {
            throw new RuntimeException("No move strategy found for: " + direction);
        }

        return moveStrategy;
    }

}
