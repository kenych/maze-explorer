package maze.domain;

import static java.util.Arrays.stream;

import java.util.Optional;

public enum Symbol {
    WALL('X'),
    EMPTY(' '),
    START('S'),
    FINISH('F'),

    N('^'), E('>'), S('v'), W('<'),

    GO_AND_BACK('*');

    private char character;

    Symbol(char character) {
        this.character = character;
    }

    public static Optional<Symbol> findByCharacter(char character) {
        return stream(Symbol.values())
                .filter(symbol -> symbol.character == character)
                .findFirst();
    }

    public char getCharacter() {
        return character;
    }

}
