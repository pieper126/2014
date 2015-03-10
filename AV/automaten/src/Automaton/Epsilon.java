package Automaton;

/**
 * Created by pieper126 on 03-03-15.
 */
public class Epsilon extends Label {

    private static Epsilon instance = null;

    private Epsilon() {
        // Exists only to defeat instantiation.
    }

    public static Epsilon getInstance() {
        if(instance == null) {
            instance = new Epsilon();
        }
        return instance;
    }
}
