package maze.domain.turn.strategy;

import static maze.domain.Instruction.LEFT;
import static maze.domain.Instruction.RIGHT;
import static maze.domain.Instruction.UTURN;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import maze.domain.Explorer;
import maze.domain.InstructionStrategy;
import maze.domain.move.UTurnUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TurnInstructionStrategyTest {

    @Mock
    private UTurnUtility uTurnUtilityMock;

    private InstructionStrategy strategy;

    @Mock
    private Explorer explorerMock;

    @Before
    public void setup() {
        strategy = new TurnInstructionStrategy(uTurnUtilityMock);
    }

    @Test
    public void followUturn() throws Exception {
        when(uTurnUtilityMock.getRandomTurn()).thenReturn(RIGHT);
        strategy.follow(explorerMock, UTURN);

        verify(explorerMock, times(2)).turn(RIGHT);
    }

    @Test
    public void followRight() throws Exception {
        strategy.follow(explorerMock, RIGHT);

        verify(explorerMock, times(1)).turn(RIGHT);
    }

    @Test
    public void followLeft() throws Exception {
        strategy.follow(explorerMock, LEFT);

        verify(explorerMock, times(1)).turn(LEFT);
    }

}