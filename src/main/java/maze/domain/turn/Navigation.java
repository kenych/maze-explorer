package maze.domain.turn;

import static java.util.stream.IntStream.range;

import maze.domain.Instruction;
import maze.domain.turn.strategy.FourDirectionalNavigationStrategy;
import maze.domain.turn.strategy.NavigationStrategy;

public class Navigation {

    private final NavigationStrategy navigationStrategy = new FourDirectionalNavigationStrategy();
    private int directionIndex = -1;

    public Navigation(Direction direction) {
        directionIndex = indexBy(direction);
    }

    private int indexBy(Direction direction) {
        return range(0, navigationStrategy.getDirections().length)
                .filter(index -> navigationStrategy.getDirections()[index] == direction)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("navigation direction not found: " + direction));
    }

    public Direction getDirection() {
        return navigationStrategy.getDirections()[directionIndex];
    }

    public void turn(Instruction instruction) {
        directionIndex = navigationStrategy.getDirectionIndex(directionIndex, instruction);
    }

}
