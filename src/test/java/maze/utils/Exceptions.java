package maze.utils;


public class Exceptions {

    private Runnable runnable;
    private Exception exception;

    public Exceptions(Runnable runnable) {
        this.runnable = runnable;
    }

    public static Exceptions assertThat(Runnable runnable) {
        return new Exceptions(runnable);
    }

    public Exceptions throwsException(Class<? extends Exception> type) {
        try {
            runnable.run();
        } catch (Exception e) {
            exception = e;
            if (e.getClass().isAssignableFrom(type)) {
                return this;
            }
            throw new AssertionError("Expected exception " + type.getName() + " , but thrown " + e.getClass().getName());
        }
        throw new AssertionError("Expected to throw an exception " + type.getName() + ", but did not");

    }

    public Exceptions withMessageContaining(String s) {
        if (exception.getMessage().contains(s)) {
            return this;
        }
        throw new AssertionError("Expected exception with message containing " + s + ", but was: " + exception.getMessage());
    }

}