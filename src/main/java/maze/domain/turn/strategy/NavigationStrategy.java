package maze.domain.turn.strategy;

import maze.domain.Instruction;
import maze.domain.turn.Direction;

public interface NavigationStrategy {
    int getDirectionIndex(int directionIndex, Instruction instruction);

    Direction[] getDirections();
}
