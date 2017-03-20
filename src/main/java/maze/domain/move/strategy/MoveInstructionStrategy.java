package maze.domain.move.strategy;

import static maze.domain.Symbol.WALL;

import maze.domain.Explorer;
import maze.domain.Instruction;
import maze.domain.InstructionStrategy;
import maze.domain.Point;
import maze.domain.Position;
import maze.domain.turn.Direction;

public class MoveInstructionStrategy implements InstructionStrategy {

    @Override
    public void follow(Explorer explorer, Instruction instruction) {
        Direction direction = explorer.getDirection();
        Position nextPosition = MoveStrategiesFactory.strategyBy(direction).getNextPositionFor(explorer.getPosition());

        validate(explorer, nextPosition);

        explorer.setPosition(nextPosition);
        explorer.addToHistory(new Point(nextPosition, direction));
//        System.out.println(new Point(nextPosition, direction));
    }

    private void validate(Explorer explorer, Position position) {
        if (explorer.getMaze().getSymbolAt(position) == null) {
            throw new IllegalArgumentException("Can not move out of wall to position: " + position);
        }

        if (explorer.getMaze().getSymbolAt(position) == WALL) {
            throw new IllegalArgumentException("Can not move to wall at position: " + position);
        }
    }

}
