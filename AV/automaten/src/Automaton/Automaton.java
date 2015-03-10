package Automaton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by pieper126 on 27-02-15.
 */
public class Automaton {

    public Entry entry;

    public ArrayList<State> states;

    public LinkedList<Label> alphabet;

    public Automaton(Entry entry, Collection<State> states, Collection<Label> alphabet) {
        this.entry = entry;
        this.states = new ArrayList<State>(states);
        this.alphabet = new LinkedList<Label>(alphabet);
    }

    protected Automaton(){

    }

    /**
     * checks if the given string is accepted by this automaton
     * @param stringToBeTested
     * @return
     */
    public boolean isStringAccepted(String stringToBeTested){
        return entry.entryPoint.isAcceptedString(stringToBeTested);
    }

    /**
     * checks if a Automaton is finite and deterministic.
     * @return true if it is a finite and deterministic.
     */
    public boolean isDeterministicFiniteAutomata(){
        if (!hasAFinalState()) return false;
        if (alphabet.contains(Epsilon.getInstance())) return false;
        if (!allStatesHaveAllLables()) return false;

        return true;
    }

    /**
     * test if all states implement all lables
     * @return
     */
    private boolean allStatesHaveAllLables(){
        for (State state: states){
            if (!state.testIfAllLablesArePresent(alphabet)) return false;
        }
        return true;
    }

    /**
     * test if this automaton has a finalstate
     * @return
     */
    private boolean hasAFinalState(){
        for (State state : states){
            if (state.isFinalState()) return true;
        }

        return false;
    }
}
