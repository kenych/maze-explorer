package maze.domain;

import static com.google.common.io.Resources.getResource;
import static java.nio.charset.Charset.defaultCharset;
import static maze.TestHelpers.givenMaze;
import static maze.domain.Symbol.START;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.io.IOException;

import com.google.common.io.Resources;
import org.junit.Test;

public class MazeTest {

    @Test
    public void testSize() {
        Maze maze = givenMaze("Maze5.txt");
        assertThat(maze.getRowSize()).isEqualTo(6);
        assertThat(maze.getColumnSize()).isEqualTo(15);
    }

    @Test
    public void testPlaceAt() {
        Maze maze = new Maze(5, 5);
        maze.placeSymbol(START, new Position(0, 0));
        assertThat(maze.getSymbolAt(new Position(0, 0))).isEqualTo(START);
        assertThat(maze.getSymbolAt(new Position(10, 10))).isNull();
    }

    @Test
    public void testPrint() throws IOException {
        Maze maze = givenMaze("Maze3.txt");
        Explorer explorer = Explorer.dropIn(maze);
        explorer.findExit();
        explorer.reportHistory();

        String actualPrint = maze.print();

        String expectedPrint = Resources.toString(
                getResource("Maze3_Print.txt"),
                defaultCharset());

        assertThat(actualPrint).isEqualTo(expectedPrint);
    }

}