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

    public State(String stateName) {
        this.stateName = stateName;
        this.transitions = new ArrayList<Transition>();
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
                break;
            }

            return false;
        }

        return true;
    }
}
