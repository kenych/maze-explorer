package maze.domain;

import java.util.LinkedList;
import java.util.List;

import maze.domain.turn.Direction;

public class Junction {

    private Direction direction;
    private List<Instruction> instructions = new LinkedList<>();
    private List<Instruction> moveOptions;

    public Junction(Direction direction, List<Instruction> moveOptions) {
        this.direction = direction;
        this.moveOptions = moveOptions;
    }

    public void addHistory(Instruction instruction) {
        instructions.add(instruction);
    }

    public Direction getDirection() {
        return direction;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public List<Instruction> getMoveOptions() {
        return moveOptions;
    }

    @Override
    public String toString() {
        return "Junction{" +
                "direction=" + direction +
                ", instructions=" + instructions +
                ", moveOptions=" + moveOptions +
                '}';
    }
}
