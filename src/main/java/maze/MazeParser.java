package maze;

import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.of;
import static maze.domain.Symbol.EMPTY;
import static maze.domain.Symbol.FINISH;
import static maze.domain.Symbol.START;
import static maze.domain.Symbol.WALL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maze.domain.Maze;
import maze.domain.Position;
import maze.domain.Symbol;

public class MazeParser {

    private Map<Symbol, Integer> symbolsMap;

    public Maze parse(List<String> lines) {
        initialiseSymbolsMap();
        Maze maze = new Maze(lines.get(0).length(), lines.size());

        range(0, lines.size()).forEach(lineNumber -> {
            String line = lines.get(lineNumber);
            int rowNumber = lines.size() - 1 - lineNumber;

            range(0, line.length()).forEach(columnNumber -> {
                char character = line.charAt(columnNumber);

                Symbol symbol = Symbol.findByCharacter(character)
                        .orElseThrow(() -> new IllegalArgumentException("wrong symbol: " + character + " at line: '" + line + "'"));

                symbolsMap.put(symbol, incrementedNumberOf(symbol));

                maze.placeSymbol(symbol, new Position(columnNumber, rowNumber));

                if (symbol == START) {
                    maze.markStartPoint(new Position(columnNumber, rowNumber));
                }
            });
        });

        return maze.withSymbolsMap(validatedSymbolsMap());
    }

    private int incrementedNumberOf(Symbol symbol) {
        return symbolsMap.get(symbol) + 1;
    }

    private Map<Symbol, Integer> validatedSymbolsMap() {
        of(FINISH, START)
                .filter(symbol -> symbolsMap.get(symbol) > 1 || symbolsMap.get(symbol) == 0)
                .findAny().ifPresent((symbol) -> {
            throw new IllegalArgumentException("There must be one " + symbol + "");
        });

        return symbolsMap;
    }

    private void initialiseSymbolsMap() {
        symbolsMap = new HashMap<>();
        symbolsMap.put(FINISH, 0);
        symbolsMap.put(START, 0);
        symbolsMap.put(EMPTY, 0);
        symbolsMap.put(WALL, 0);
    }

}
