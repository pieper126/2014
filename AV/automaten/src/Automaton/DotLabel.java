package Automaton;

/**
 * Created by pieper126 on 03-03-15.
 */
public class DotLabel extends Label {

    private static DotLabel instance = null;

    private DotLabel() {
        // Exists only to defeat instantiation.
    }

    public static DotLabel getInstance() {
        if(instance == null) {
            instance = new DotLabel();
        }
        return instance;
    }
}
