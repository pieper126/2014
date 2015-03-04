package Automaton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by pieper126 on 27-02-15.
 */
public class Automaton {

    public Entry entry;

    public ArrayList<Final> finals;

    public ArrayList<State> states;

    public LinkedList<Label> alphabet;

    public Automaton(Entry entry, Collection<State> states, Collection<Final> finals, Collection<Label> alphabet) {
        this.entry = entry;
        this.states = new ArrayList<State>(states);
        this.finals = new ArrayList<Final>(finals);
        this.alphabet = new LinkedList<Label>(alphabet);
    }

    public Automaton(String entry, Collection<State> states, Collection<Final> finals, Collection<Label> alphabet) {
        this.entry = new Entry(entry);
        this.states = new ArrayList<State>(states);
        this.finals = new ArrayList<Final>(finals);
        this.alphabet = new LinkedList<Label>(alphabet);
    }

    /**
     * checks if a Automaton is finite and deterministic.
     * @return true if it is a finite and deterministic.
     */
    public boolean isDeterministicFiniteAutomata(){
        if (finals.size() < 1) return false;
        if (!allStatesHaveAllLables()) return false;

        return true;
    }

    /**
     * test if all states implement all lables
     * @return
     */
    public boolean allStatesHaveAllLables(){
        for (State state: states){
            if (!state.testIfAllLablesArePresent(alphabet)) return false;
        }
        return true;
    }
}
