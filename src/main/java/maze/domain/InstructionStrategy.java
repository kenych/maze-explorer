package maze.domain;

public interface InstructionStrategy {
    void follow(Explorer explorer, Instruction instruction);
}
