package maze.domain.turn.strategy;

import static maze.domain.Instruction.UTURN;

import maze.domain.Explorer;
import maze.domain.Instruction;
import maze.domain.InstructionStrategy;
import maze.domain.move.UTurnUtility;

public class TurnInstructionStrategy implements InstructionStrategy {

    private UTurnUtility uTurnUtility;

    public TurnInstructionStrategy(UTurnUtility uTurnUtility) {
        this.uTurnUtility = uTurnUtility;
    }

    @Override
    public void follow(Explorer explorer, Instruction instruction) {
        if (instruction == UTURN) {
            Instruction randomTurn = uTurnUtility.getRandomTurn();
            explorer.turn(randomTurn);
            explorer.turn(randomTurn);
        } else {
            explorer.turn(instruction);
        }
    }
}
