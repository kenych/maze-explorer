package maze.domain;

import java.util.Map;

public class Maze {

    private Symbol[][] cells;

    private Position startPoint;

    private Map<Symbol, Integer> symbolsMap;

    public Maze(int columns, int rows) {
        this.cells = new Symbol[columns][rows];
    }

    public Maze withSymbolsMap(Map<Symbol, Integer> symbolsMap) {
        this.symbolsMap = symbolsMap;
        return this;
    }

    public int getNumberOfSymbols(Symbol symbol) {
        return symbolsMap.get(symbol);
    }

    public void placeSymbol(Symbol symbol, Position position) {
        cells[position.getX()][position.getY()] = symbol;
    }

    public int getRowSize() {
        return cells[0].length;
    }

    public int getColumnSize() {
        return cells.length;
    }

    public Symbol getSymbolAt(Position position) {
        try {
            return cells[position.getX()][position.getY()];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        //no decrementing range in java8?
        // ...sticking with for
        for (int y = getRowSize() - 1; y >= 0; y--) {
            for (int x = 0; x < getColumnSize(); x++) {
                stringBuilder.append(getSymbolAt(new Position(x, y)).getCharacter());
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void markStartPoint(Position position) {
        startPoint = position;
    }

    public Position getStartPoint() {
        return startPoint;
    }
}
