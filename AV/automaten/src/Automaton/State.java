package Automaton;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by pieper126 on 27-02-15.
 */
public class State {

    private ArrayList<Transition> transitions;

    private String stateName;

    private Boolean finalState;

    public State(String stateName) {
        this.stateName = stateName;
        this.finalState = false;
        this.transitions = new ArrayList<Transition>();
    }

    protected State() {
        this.finalState = false;
    }

    /**
     * gets all the transitions from the state
     *
     * @return
     */
    public Collection<Transition> getTransitions() {
        return Collections.unmodifiableCollection(transitions);
    }

    /**
     * gets the name of the state
     *
     * @return
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * checks if this state is a finalstate
     *
     * @return
     */
    public boolean isFinalState() {
        return finalState;
    }

    /**
     * sets the state to final
     */
    public void setFinal() {
        this.finalState = true;
    }

    public void unFinal(){
        this.finalState = false;
    }

    /**
     * test if the String is accepted by this state
     *
     * @param string characters this State should accept
     * @return
     */
    public boolean isAcceptedString(String string) {
        if (finalState && string.isEmpty()) {
            return true;
        } else if (string.isEmpty()) {
            for (Transition transition : transitions){
                if (!transition.getTo().equals(this) && transition.getTo().isAcceptedString(string) && transition.getLabel().getClass().getName().equals(Epsilon.getInstance().getClass().getName()) ) return true;
            }
            return false;
        }

        String characterToBeTested = string.substring(0, 1);
        boolean accepted = false;

        for (Transition transition : transitions) {
            if (transition.getLabel().getClass().getName().equals(Epsilon.getInstance().getClass().getName())) {
                accepted = transition.getTo().isAcceptedString(string);

                if (accepted) return accepted;
            } else if (transition.getLabel().getClass().getName().equals(DotLabel.getInstance().getClass().getName())) {
                accepted = transition.getTo().isAcceptedString(string.substring(1));

                if (accepted) return accepted;
            } else if (((DefinedLabel) transition.getLabel()).definedLabel.equals(characterToBeTested)) {
                accepted = transition.getTo().isAcceptedString(string.substring(1));

                if (accepted) return accepted;
            }
        }

        return false;
    }

    /**
     * add a transition to this state
     *
     * @param transition
     */
    public void addTransition(Transition transition) throws InvalidArgumentException {
        if (transition == null || !transition.getFrom().equals(this) && !transition.getTo().equals(this))
            throw new InvalidArgumentException(new String[]{"both from and to don't equal this State"});

        transitions.add(transition);
    }

    /**
     * @param labels
     * @return
     */
    public boolean testIfAllLablesArePresent(Collection<Label> labels) {
        boolean found = false;

        for (Transition transition : transitions) {
            for (Label label : labels) {
                if (label.equals(transition.getLabel())) {
                    found = true;
                    break;
                }
            }

            if (found) {
                found = false;
                continue;
            }

            return false;
        }

        return true;
    }
}
