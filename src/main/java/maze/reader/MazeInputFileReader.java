package maze.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MazeInputFileReader {

    private final String inputFile;

    public MazeInputFileReader(String inputFile) {
        this.inputFile = inputFile;
    }

    public List<String> read() {
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(MazeInputFileReader.class.getClassLoader().getResource(inputFile).getFile()))) {

            return validated(bufferedReader.lines().collect(Collectors.toList()));

        } catch (FileNotFoundException e) {
            throw new RuntimeException("input file not found: " + inputFile);
        } catch (IOException e) {
            throw new RuntimeException("file " + inputFile + " read error+:" + e.getMessage());
        }
    }

    private List<String> validated(List<String> lines) {
        int firstLineColumnSize = lines.get(0).length();

        lines.stream().filter(line -> line.length() != firstLineColumnSize).findAny().ifPresent(inputFile -> {
            throw new IllegalArgumentException("Column size 12 is wrong for");
        });

        return lines;
    }


}
