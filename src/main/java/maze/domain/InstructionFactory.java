package maze.domain;

import static maze.domain.Instruction.MOVE;

import maze.domain.move.UTurnUtility;
import maze.domain.move.strategy.MoveInstructionStrategy;
import maze.domain.turn.strategy.TurnInstructionStrategy;

public class InstructionFactory {

    private static InstructionStrategy moveInstruction = new MoveInstructionStrategy();
    private static InstructionStrategy turnInstruction = new TurnInstructionStrategy(new UTurnUtility());

    //we use extended 8 directions so strategy is different
//    private static TurnInstructionStrategy turnInstruction = new TurnInstructionStrategy();

    public static InstructionStrategy strategyFor(Instruction instruction) {
        return instruction == MOVE ? moveInstruction : turnInstruction;
    }
}
