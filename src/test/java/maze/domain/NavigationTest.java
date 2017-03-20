package maze.domain;

import static maze.domain.Instruction.LEFT;
import static maze.domain.Instruction.RIGHT;
import static maze.domain.turn.Direction.E;
import static maze.domain.turn.Direction.N;
import static maze.domain.turn.Direction.S;
import static maze.domain.turn.Direction.SW;
import static maze.domain.turn.Direction.W;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import maze.domain.turn.Navigation;
import maze.utils.Exceptions;
import org.junit.Test;

public class NavigationTest {


    @Test
    public void testTurnWithFourDirectionalNavigationStrategy() throws Exception {
        Navigation navigation = new Navigation(S);

        navigation.turn(RIGHT);
        assertThat(navigation.getDirection()).isEqualTo(W);

        navigation.turn(RIGHT);
        assertThat(navigation.getDirection()).isEqualTo(N);

        navigation.turn(RIGHT);
        assertThat(navigation.getDirection()).isEqualTo(E);

        navigation.turn(LEFT);
        assertThat(navigation.getDirection()).isEqualTo(N);
    }

    @Test
    public void testFailWhenUsingWrongNavigationStrategy() {
        Exceptions.assertThat((() -> new Navigation(SW)))
                .throwsException(IllegalArgumentException.class)
                .withMessageContaining("navigation direction not found: SW");
    }

}
