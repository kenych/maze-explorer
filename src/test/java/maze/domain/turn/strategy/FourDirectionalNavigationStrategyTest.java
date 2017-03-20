package maze.domain.turn.strategy;

import static maze.domain.Instruction.LEFT;
import static maze.domain.Instruction.RIGHT;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.Test;

public class FourDirectionalNavigationStrategyTest {

    private NavigationStrategy navigationStrategy = new FourDirectionalNavigationStrategy();

    @Test
    public void getDirectionIndex() throws Exception {
        assertThat(navigationStrategy.getDirectionIndex(0, RIGHT)).isEqualTo(1);
        assertThat(navigationStrategy.getDirectionIndex(1, RIGHT)).isEqualTo(2);
        assertThat(navigationStrategy.getDirectionIndex(2, RIGHT)).isEqualTo(3);
        assertThat(navigationStrategy.getDirectionIndex(3, RIGHT)).isEqualTo(0);
        assertThat(navigationStrategy.getDirectionIndex(0, LEFT)).isEqualTo(3);
    }

}