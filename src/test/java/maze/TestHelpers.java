package maze;

import maze.domain.Maze;
import maze.reader.MazeInputFileReader;

public class TestHelpers {
    public static Maze givenMaze(String mazeName) {
        return new MazeParser().parse(new MazeInputFileReader(mazeName).read());
    }
}
