package Automaton.StandardRegExAutomatons;

import Automaton.*;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by pieper126 on 08-03-15.
 */
public class SingleCharAutomaton extends Automaton {

    /**
     * creates a automaton that accepts only one character
     *
     * @param input
     */
    public SingleCharAutomaton(String input) {
        State state = new State(input);
        super.states = new ArrayList<>();
        states.add(state);

        Label label = new DefinedLabel(input);
        super.alphabet = new LinkedList<>();
        alphabet.add(label);

        super.entry = new Entry(state);
    }

    /**
     *
     * @param transition
     */
    public void addingTransition(Transition transition) {

    }
}
