package Automaton;

/**
 * Created by pieper126 on 27-02-15.
 */
public class Transition {

    private Label label;

    private State from;

    private State to;

    public Transition(State from, State to, Label label) {
        this.label = label;
        this.from = from;
        this.to = to;
    }

    public Label getLabel() {
        return label;
    }

    public State getFrom() {
        return from;
    }

    public State getTo() {
        return to;
    }
}
