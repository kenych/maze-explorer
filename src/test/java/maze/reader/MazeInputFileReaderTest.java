package maze.reader;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import maze.utils.Exceptions;
import org.junit.Test;

public class MazeInputFileReaderTest {

    public static final String CORRECT_INPUT_FILE = "Maze1.txt";
    public static final String INCORRECT_INPUT_FILE = "Maze_INCORRECT_COLUMN_SIZE.txt";

    @Test
    public void testCorrectFileRead() throws Exception {
        List<String> lines = new MazeInputFileReader(CORRECT_INPUT_FILE).read();

        assertThat(lines).hasSize(15);
        assertThat(lines.get(3)).isEqualTo("X XS        X X");
    }

    @Test
    public void testCorruptedFileRead() throws Exception {
        Exceptions.assertThat((() -> new MazeInputFileReader(INCORRECT_INPUT_FILE).read()))
                .throwsException(IllegalArgumentException.class)
                .withMessageContaining("Column size 12 is wrong for");
    }
}
