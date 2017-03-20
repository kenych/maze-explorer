package maze.domain;

import static java.util.stream.IntStream.rangeClosed;
import static java.util.stream.Stream.of;
import static maze.domain.Instruction.LEFT;
import static maze.domain.Instruction.MOVE;
import static maze.domain.Instruction.RIGHT;
import static maze.domain.Instruction.UTURN;
import static maze.domain.InstructionFactory.strategyFor;
import static maze.domain.Symbol.EMPTY;
import static maze.domain.Symbol.FINISH;
import static maze.domain.Symbol.START;
import static maze.domain.turn.Direction.N;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import maze.domain.move.UTurnUtility;
import maze.domain.move.strategy.MoveStrategiesFactory;
import maze.domain.turn.Direction;
import maze.domain.turn.Navigation;

public class Explorer {

    private static final Direction DEFAULT_DIRECTION = N;
    Deque<Junction> junctions = new LinkedList<>();
    private Maze maze;
    private Position position;
    private Navigation navigation;
    private LinkedList<Point> history = new LinkedList<>();
    private UTurnUtility uTurnUtility;
    private boolean goingBack;

    private Explorer(Maze maze, UTurnUtility uTurnUtility) {
        this.maze = maze;
        position = maze.getStartPoint();
        this.uTurnUtility = uTurnUtility;
        navigation = new Navigation(DEFAULT_DIRECTION);
        history.add((new Point(position, getDirection())));
    }

    public static Explorer dropIn(Maze maze) {
        return new Explorer(maze, new UTurnUtility());
    }

    public void reportHistory() {
        history.stream()
                .filter(this::isNotStartOrFinishPosition)
                .forEach(point -> {
                    if (maze.getSymbolAt(point.getPosition()) == Symbol.EMPTY) {
                        maze.placeSymbol(Symbol.valueOf(point.getDirection().name()), point.getPosition());
                    } else {
                        maze.placeSymbol(Symbol.GO_AND_BACK, point.getPosition());
                    }
                });
    }

    private boolean isNotStartOrFinishPosition(Point point) {
        Symbol symbolAt = maze.getSymbolAt(point.getPosition());
        return symbolAt != START
                && symbolAt != FINISH;
    }

    public Symbol getWhatIsInFront() {
        return maze.getSymbolAt(MoveStrategiesFactory.strategyBy(getDirection()).getNextPositionFor(getPosition()));
    }

    public List<Instruction> getMoveOptions() {
        List<Instruction> moveOptions = new ArrayList<>();

        if (getWhatIsInFront() == EMPTY) {
            moveOptions.add(MOVE);
        }

        of(RIGHT, LEFT).filter(this::isEmptyAt).forEach(moveOptions::add);

        /* at start point 4th point, which is at the back, must be looked up as well
           in all other cases we don't consider the way you came from as movement option,
           but as escape option from trap when maze has junctions with multiple ways
         */
        if (isStartPoint() && isEmptyAfterUTurn(uTurnUtility.getRandomTurn())) {
            moveOptions.add(UTURN);
        }

        return moveOptions;
    }

    private boolean isStartPoint() {
        return getPosition() == maze.getStartPoint();
    }

    private boolean isEmptyAt(Instruction instruction, int repeatTimes) {
        boolean result;

        rangeClosed(1, repeatTimes).forEach(i -> turn(instruction));
        result = getWhatIsInFront() == EMPTY;
        rangeClosed(1, repeatTimes).forEach(i -> turn(instruction.opposite()));

        return result;
    }

    private boolean isEmptyAt(Instruction instruction) {
        return isEmptyAt(instruction, 1);
    }

    private boolean isEmptyAfterUTurn(Instruction instruction) {
        return isEmptyAt(instruction, 2);
    }

    /**
     * Simple find exit solution for testing history and report, assuming simple maze with single
     * path to exit with no junctions and traps.
     */
    public Position findExit() {
        Symbol symbol;
        while ((symbol = getWhatIsInFront()) != FINISH && turnAndCheckIfFinishIsNotOnSides()) {
            if (symbol != EMPTY) {
                follow(directionWithFreeWayOrFail());
            }
            follow(MOVE);
        }
        //final step, YAY!
        return follow(MOVE).getPosition();
    }

    /**
     * More complex maze with multiple traps
     */
    public Optional<Position> findExit2() {
        while ((getWhatIsInFront()) != FINISH && turnAndCheckIfFinishIsNotOnSides()) {
            List<Instruction> moveOptions = getMoveOptions();

            //junction
            if (moveOptions.size() > 1) {
                Junction junction = new Junction(getDirection(), moveOptions);
                System.out.println(junction);
                junctions.push(junction);

                for (Instruction moveOption : moveOptions) {
                    turnIfNeededAndMove(moveOption);
                    Optional<Position> positionOptional = findExit2();
                    if (!positionOptional.isPresent()) {
                        goBack();
                    } else {
                        return positionOptional;
                    }
                }
                System.out.println("all ways of junction gone");
                junctions.pop();

            } else if (moveOptions.size() == 1) {
                Instruction instruction = moveOptions.get(0);
                turnIfNeededAndMove(instruction);
            } else {
                return Optional.empty();
            }
        }

        //final step, YAY!
        return Optional.of(follow(MOVE).getPosition());
    }

    private void turnIfNeededAndMove(Instruction moveOption) {
        follow(moveOption);
        if (moveOption != MOVE) {
            follow(MOVE);
        }
    }

    void goBack() {
        goingBack = true;
        Junction junction = junctions.peek();
        if (junction != null) {
            follow(UTURN);
            Collections.reverse(junction.getInstructions());
            junction.getInstructions().forEach(instruction -> follow(instruction == MOVE ? MOVE : instruction.opposite()));
            while (getDirection() != junction.getDirection()) {
                follow(RIGHT);
            }
        }
        goingBack = false;
    }

    private Instruction directionWithFreeWayOrFail() {
        return getMoveOptions()
                .stream()
                .filter(instruction -> instruction != MOVE).findAny()
                .orElseThrow(() -> new TrapException("No ways found!"));
    }

    public Maze getMaze() {
        return maze;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Point> getHistory() {
        return Collections.unmodifiableList(history);
    }

    public void addToHistory(Point point) {
        history.add(point);
    }

    public Direction getDirection() {
        return navigation.getDirection();
    }

    public Explorer follow(Instruction instruction) {
        Junction junction = junctions.peek();
        if (junction != null && !goingBack) {
            junction.addHistory(instruction);
        }
        strategyFor(instruction).follow(this, instruction);
        return this;
    }

    public void turn(Instruction instruction) {
        navigation.turn(instruction);
    }

    public boolean turnAndCheckIfFinishIsNotOnSides() {
        return turnAndCheckIfFinishIsNotOnSide(RIGHT) && turnAndCheckIfFinishIsNotOnSide(LEFT);
    }

    private boolean turnAndCheckIfFinishIsNotOnSide(Instruction instruction) {
        turn(instruction);
        if (getWhatIsInFront() != FINISH) {
            turn(instruction.opposite());
            return true;
        } else {
            return false;
        }
    }
}
