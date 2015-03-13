package Automaton.StandardRegExAutomatons;

import Automaton.*;
import com.sun.javaws.exceptions.InvalidArgumentException;

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
        // create the given state
        State state = new State(input);
        super.states = new ArrayList<>();
        super.states.add(state);

        // give the given state the final status
        state.setFinal();
        super.finals = new LinkedList<>();
        super.finals.add(state);

        // define the given label
        Label label = new DefinedLabel(input);
        super.alphabet = new LinkedList<>();
        alphabet.add(label);

        // create a entry point and add it to the states
        State entryPoint = new State("s");
        super.states.add(entryPoint);
        super.entry = new Entry(entryPoint);

        // create the transition from entry to the first state
        Transition transition = new Transition(entryPoint, state, label);

        // add the transition to the entry point
        try {
            entryPoint.addTransition(transition);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param transition
     */
    public void addingTransition(Transition transition) {

    }
}
