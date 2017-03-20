package maze.domain.move.strategy;

import static maze.domain.Instruction.MOVE;
import static maze.domain.Symbol.EMPTY;
import static maze.domain.Symbol.WALL;
import static maze.domain.turn.Direction.N;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import maze.domain.Explorer;
import maze.domain.InstructionStrategy;
import maze.domain.Maze;
import maze.domain.Point;
import maze.domain.Position;
import maze.domain.Symbol;
import maze.utils.Exceptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MoveInstructionStrategyTest {

    private static final Symbol SYMBOL_OUT_OF_MAZE = null;
    private InstructionStrategy strategy = new MoveInstructionStrategy();

    @Mock
    private Maze mazeMock;

    @Mock
    private Explorer explorer;

    @Before
    public void setup() {
        when(explorer.getMaze()).thenReturn(mazeMock);
        when(explorer.getDirection()).thenReturn(N);
        when(explorer.getPosition()).thenReturn(new Position(0, 0));
    }

    @Test
    public void testFollowMoveSuccessful() throws Exception {
        when(mazeMock.getSymbolAt(any(Position.class))).thenReturn(EMPTY);

        strategy.follow(explorer, MOVE);

        verify(explorer).setPosition(any(Position.class));
        verify(explorer).addToHistory(any(Point.class));
    }

    @Test
    public void testFollowMoveFailWhenOutOfMaze() throws Exception {
        when(mazeMock.getSymbolAt(any(Position.class))).thenReturn(SYMBOL_OUT_OF_MAZE);

        Exceptions.assertThat((() -> strategy.follow(explorer, MOVE)))
                .throwsException(IllegalArgumentException.class)
                .withMessageContaining("Can not move out of wall");

        verify(explorer, times(0)).setPosition(any(Position.class));
        verify(explorer, times(0)).addToHistory(any(Point.class));
    }

    @Test
    public void testFollowMoveFailWhenMovingToWall() throws Exception {
        when(mazeMock.getSymbolAt(any(Position.class))).thenReturn(WALL);

        Exceptions.assertThat((() -> strategy.follow(explorer, MOVE)))
                .throwsException(IllegalArgumentException.class)
                .withMessageContaining("Can not move to wall");

        verify(explorer, times(0)).setPosition(any(Position.class));
        verify(explorer, times(0)).addToHistory(any(Point.class));
    }

}