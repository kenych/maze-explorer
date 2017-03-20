package maze;

import static java.util.Arrays.asList;
import static maze.domain.Symbol.EMPTY;
import static maze.domain.Symbol.FINISH;
import static maze.domain.Symbol.START;
import static maze.domain.Symbol.WALL;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import maze.domain.Maze;
import maze.domain.Position;
import maze.reader.MazeInputFileReader;
import maze.utils.Exceptions;
import org.junit.Test;

public class MazeParserTest {

    @Test
    public void parse() throws Exception {
        List<String> lines = new MazeInputFileReader("Maze1.txt").read();

        Maze maze = new MazeParser().parse(lines);

        assertThat(maze.getSymbolAt(new Position(0, 0))).isEqualTo(WALL);
        assertThat(maze.getSymbolAt(new Position(1, 0))).isEqualTo(FINISH);
        assertThat(maze.getSymbolAt(new Position(3, 11))).isEqualTo(START);
        assertThat(maze.getSymbolAt(new Position(1, 1))).isEqualTo(EMPTY);

        assertThat(maze.getStartPoint()).isEqualTo(new Position(3, 11));
    }

    @Test
    public void testMazeDimensions() {
        Maze maze = new MazeParser().parse(asList("XXX", "S F"));

        assertThat(maze.getColumnSize()).isEqualTo(3);
        assertThat(maze.getRowSize()).isEqualTo(2);

        assertThat(maze.getNumberOfSymbols(WALL)).isEqualTo(3);
        assertThat(maze.getNumberOfSymbols(START)).isEqualTo(1);
        assertThat(maze.getNumberOfSymbols(FINISH)).isEqualTo(1);
        assertThat(maze.getNumberOfSymbols(EMPTY)).isEqualTo(1);
    }

    @Test
    public void parseWithErrorSymbol() throws Exception {
        Exceptions.assertThat((() -> new MazeParser().parse(asList("XX", " N"))))
                .throwsException(IllegalArgumentException.class)
                .withMessageContaining("wrong symbol: N");
    }

    @Test
    public void parseWithErrorDoubleOrZeroStartAndFinish() throws Exception {
        Exceptions.assertThat((() -> new MazeParser().parse(asList("XX", " F"))))
                .throwsException(IllegalArgumentException.class)
                .withMessageContaining("There must be one START");

        Exceptions.assertThat((() -> new MazeParser().parse(asList("XX", " S"))))
                .throwsException(IllegalArgumentException.class)
                .withMessageContaining("There must be one FINISH");

        Exceptions.assertThat((() -> new MazeParser().parse(asList("SS", "F "))))
                .throwsException(IllegalArgumentException.class)
                .withMessageContaining("There must be one START");

        Exceptions.assertThat((() -> new MazeParser().parse(asList("FF", "S "))))
                .throwsException(IllegalArgumentException.class)
                .withMessageContaining("There must be one FINISH");
    }

}