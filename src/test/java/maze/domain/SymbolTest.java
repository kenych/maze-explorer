package maze.domain;

import static maze.domain.Symbol.WALL;
import static maze.domain.Symbol.findByCharacter;
import static maze.domain.turn.Direction.E;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class SymbolTest {
    @Test
    public void testFindByCharacter() throws Exception {
        assertThat(findByCharacter('X').get()).isEqualTo(WALL);
        assertThat(findByCharacter('M').isPresent()).isFalse();
        assertThat(Symbol.valueOf(E.name()).getCharacter()).isEqualTo('>');
    }

}