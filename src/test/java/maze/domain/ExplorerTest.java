package maze.domain;

import static maze.TestHelpers.givenMaze;
import static maze.domain.Instruction.MOVE;
import static maze.domain.Instruction.RIGHT;
import static maze.domain.Instruction.UTURN;
import static maze.domain.Symbol.WALL;
import static maze.domain.turn.Direction.E;
import static maze.domain.turn.Direction.N;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.Stream;

import maze.utils.Exceptions;
import org.junit.Test;

public class ExplorerTest {

    private static final String MAZE_1 = "Maze1.txt";
    private static final String MAZE_2 = "Maze2.txt";
    private static final String MAZE_3 = "Maze3.txt";
    private static final String MAZE_4 = "Maze4.txt";
    private static final String MAZE_6 = "Maze6.txt";
    private static final String MAZE_7 = "Maze7.txt";
    private static final Position MAZE_3_FINISH = new Position(3, 12);
    private static final Position MAZE_2_FINISH = new Position(1, 0);

    @Test
    public void testDropIn() {
        Maze maze = givenMaze(MAZE_1);

        Explorer explorer = Explorer.dropIn(givenMaze(MAZE_1));

        assertThat(explorer.getDirection()).isEqualTo(N);
        assertThat(explorer.getPosition()).isEqualTo(maze.getStartPoint());
    }

    @Test
    public void testGetWhatIsInFront() {
        Explorer explorer = Explorer.dropIn(givenMaze(MAZE_1));

        assertThat(explorer.getWhatIsInFront()).isEqualTo(WALL);
    }

    @Test
    public void testHistory() {
        Maze maze = givenMaze(MAZE_1);
        Explorer explorer = Explorer.dropIn(maze);

        Position startPoint = maze.getStartPoint();
        assertThat(explorer.getHistory()).containsExactly(new Point(maze.getStartPoint(), N));

        explorer.follow(RIGHT);
        explorer.follow(MOVE);
        explorer.follow(MOVE);

        assertThat(explorer.getHistory()).containsExactly(
                new Point(maze.getStartPoint(), N),
                new Point(new Position(startPoint.getX() + 1, startPoint.getY()), E),
                new Point(new Position(startPoint.getX() + 2, startPoint.getY()), E));
    }

    @Test
    public void testFailToMoveIntoWall() {
        Maze maze = givenMaze(MAZE_1);
        Explorer explorer = Explorer.dropIn(maze);

        Exceptions.assertThat((() -> explorer.follow(MOVE)))
                .throwsException(IllegalArgumentException.class)
                .withMessageContaining("Can not move to wall");
    }

    @Test
    public void testFindExitWhenFinishInFront() {
        Explorer explorer = Explorer.dropIn(givenMaze(MAZE_2));
        assertThat(explorer.findExit()).isEqualTo(MAZE_2_FINISH);
    }

    @Test
    public void testTrapException() {
        Exceptions.assertThat((() -> Explorer.dropIn(givenMaze(MAZE_4)).findExit()))
                .throwsException(TrapException.class)
                .withMessageContaining("No ways found!");
    }

    @Test
    public void testFindExitWhenFinishInOnTheSide() {
        Explorer explorer = Explorer.dropIn(givenMaze(MAZE_3));
        assertThat(explorer.findExit()).isEqualTo(MAZE_3_FINISH);
    }

    @Test
    public void testReport() {
        Explorer explorer = Explorer.dropIn(givenMaze(MAZE_3));
        assertThat(explorer.findExit()).isEqualTo(MAZE_3_FINISH);
    }

    @Test
    public void testGetMoveOptions() {
        Explorer explorer = Explorer.dropIn(givenMaze(MAZE_1));
        assertThat(explorer.getMoveOptions()).containsExactly(RIGHT);

        Stream.of(RIGHT, MOVE, MOVE, MOVE, MOVE, MOVE, MOVE, MOVE, MOVE, RIGHT, MOVE, MOVE, MOVE).forEach(explorer::follow);
        assertThat(explorer.getMoveOptions()).containsExactly(MOVE, RIGHT);
    }

    @Test
    public void testGetMoveOptionsWhenUTurnNeededAndOnTheEdge() {
        Explorer explorer = Explorer.dropIn(givenMaze(MAZE_3));
        assertThat(explorer.getMoveOptions()).containsExactly(UTURN);
    }

    @Test
    public void testFindExit2() {
        Maze maze = givenMaze(MAZE_6);
        Explorer explorer = Explorer.dropIn(maze);

        Optional<Position> exit2 = explorer.findExit2();
        if (exit2.isPresent()) {

            System.out.println("\nyay exit found: " + exit2.get() + "\n");
        } else {
            System.out.println("not found");
        }

        explorer.reportHistory();
        System.out.println(maze.print());

    }


}
