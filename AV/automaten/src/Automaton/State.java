package Automaton;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.*;

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
     * checks if the the state and is childern are finite
     * @return
     */
    public Boolean isFinite(ArrayList<State> didAlreadyPas){
        if (didAlreadyPas.contains(this)){
            List potentionalLoop = didAlreadyPas.subList(didAlreadyPas.indexOf(this), didAlreadyPas.size() - 1);
            for (Transition transition : transitions){
                if (potentionalLoop.contains(transition.getTo())) continue;
                if (transition.getTo().finalStateReachable(new ArrayList<>())) return false;
            }
        }

        didAlreadyPas.add(this);

        for (Transition transition : transitions){
            if (!transition.getTo().isFinite(didAlreadyPas)) return false;
        }
        return true;
    }

    /**
     * sets the state to final
     */
    public void setFinal() {
        this.finalState = true;
    }

    /**
     * sets the state unfinal
     */
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
     * checks if a final state can be reached from this point
     * @param didAlreadyPas
     * @return
     */
    public boolean finalStateReachable(ArrayList<State> didAlreadyPas){
        if (finalState){
            return true;
        }

        if (didAlreadyPas.contains(this)){
            List potentionalLoop = didAlreadyPas.subList(didAlreadyPas.indexOf(this), didAlreadyPas.size() - 1);
            for (Transition transition : transitions){
                if (potentionalLoop.contains(transition.getTo())) continue;
                transition.getTo().finalStateReachable(didAlreadyPas);
            }
        }

        didAlreadyPas.add(this);

        for (Transition transition : transitions){
            transition.getTo().finalStateReachable(didAlreadyPas);
        }

        return false;
    }

    /**
     * @param labels
     * @return
     */
    public boolean testIfAllLabelsArePresent(Collection<Label> labels) {
        boolean found = false;

        for (Transition transition : transitions) {
            for (Label label : labels) {
                if (!label.getClass().getName().equals(Epsilon.getInstance().getClass().getName())){
                    if (label.equals(transition.getLabel())) {
                        found = true;
                        break;
                    }
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


    public ArrayList<String> generatePossibleWords(Collection<Label> labelsTillNow){
        ArrayList<String> returnValue = new ArrayList<>();

        for (Transition transition : transitions){
            labelsTillNow.add(transition.getLabel());
            returnValue.addAll(transition.getTo().generatePossibleWords(labelsTillNow));
        }


        if (finalState){

            for (Label label : labelsTillNow){

            }
        }

        return returnValue;
    }
}
