package maze.domain.turn.strategy;

import static maze.domain.Instruction.LEFT;
import static maze.domain.Instruction.RIGHT;
import static maze.domain.turn.Direction.E;
import static maze.domain.turn.Direction.N;
import static maze.domain.turn.Direction.S;
import static maze.domain.turn.Direction.W;

import maze.domain.Instruction;
import maze.domain.turn.Direction;

public class FourDirectionalNavigationStrategy implements NavigationStrategy {

    @Override
    public int getDirectionIndex(int directionIndex, Instruction instruction) {
        if (instruction.equals(RIGHT)) {
            directionIndex++;
        } else if (instruction.equals(LEFT)) {
            directionIndex--;
        } else throw new IllegalArgumentException("Turn: " + instruction + " not found");

        return resetIfFullLoopAndGet(directionIndex);
    }

    @Override
    public Direction[] getDirections() {
        return new Direction[]{N, E, S, W};
    }

    private int resetIfFullLoopAndGet(int directionIndex) {
        if (directionIndex == 4) {
            directionIndex = 0;
        } else if (directionIndex == -1) {
            directionIndex = 3;
        }

        return directionIndex;
    }


}
