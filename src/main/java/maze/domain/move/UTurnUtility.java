package maze.domain.move;

import static maze.domain.Instruction.LEFT;
import static maze.domain.Instruction.RIGHT;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import maze.domain.Instruction;

/**
 * just worn both wheels evenly for u-turns:)
 */

public class UTurnUtility {
    private static final List<Instruction> INSTRUCTIONS = Arrays.asList(RIGHT, LEFT);
    private static Random random = new Random();

    public Instruction getRandomTurn() {
        return INSTRUCTIONS.get(random.ints(0, INSTRUCTIONS.size()).findAny().getAsInt());
    }
}
