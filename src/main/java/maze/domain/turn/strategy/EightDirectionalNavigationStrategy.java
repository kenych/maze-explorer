package maze.domain.turn.strategy;


import static maze.domain.Instruction.HALF_LEFT;
import static maze.domain.Instruction.HALF_RIGHT;
import static maze.domain.Instruction.LEFT;
import static maze.domain.Instruction.RIGHT;
import static maze.domain.turn.Direction.E;
import static maze.domain.turn.Direction.N;
import static maze.domain.turn.Direction.NE;
import static maze.domain.turn.Direction.NW;
import static maze.domain.turn.Direction.S;
import static maze.domain.turn.Direction.SE;
import static maze.domain.turn.Direction.SW;
import static maze.domain.turn.Direction.W;

import maze.domain.Instruction;
import maze.domain.turn.Direction;

public class EightDirectionalNavigationStrategy implements NavigationStrategy {

    @Override
    public int getDirectionIndex(int directionIndex, Instruction instruction) {

        if (instruction.equals(RIGHT)) {
            directionIndex = directionIndex + 2;
        } else if (instruction.equals(LEFT)) {
            directionIndex = directionIndex - 2;
        } else if (instruction.equals(HALF_RIGHT)) {
            directionIndex = directionIndex + 1;
        } else if (instruction.equals(HALF_LEFT)) {
            directionIndex = directionIndex - 1;
        } else throw new IllegalArgumentException("Turn: " + instruction + " not found");

        return resetIfFullLoop(directionIndex);
    }

    @Override
    public Direction[] getDirections() {
        return new Direction[]{N, NE, E, SE, S, SW, W, NW};
    }

    private int resetIfFullLoop(int directionIndex) {
        if (directionIndex > 7) {
            directionIndex = directionIndex - 8;
        } else if (directionIndex < 0) {
            directionIndex = 8 + directionIndex;
        }
        return directionIndex;
    }


}
