package Parser;

import Automaton.*;
import Exceptions.InCorrectFormatException;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Parser {

    private static String COMMENT = "#";
    private static String ALPHABET = "alfabet:";
    private static String STATES = "states:";
    private static String FINAL = "final:";
    private static String TRANSITIONS = "transitions:";

    public static Automaton parse(String input) throws InCorrectFormatException {
        String[] splitText = input.split("\\n");
        Collection<Label> alphabet = null;
        Collection<State> states = null;
        Entry entry = null;
        Collection<Transition> transitions = null;


        int counter = 0;


        while (counter < splitText.length) {

            // if there are comments in the input we should ignore it
            if (splitText[counter].startsWith(COMMENT)) {
                // do nothing
            } else if (splitText[counter].startsWith(ALPHABET)) {
                alphabet = parseAlphabet(splitText[counter]);
            } else if (splitText[counter].startsWith(STATES)) {
                states = parseState(splitText[counter]);
                entry = new Entry((State) ((states.toArray())[0]));
            } else if (splitText[counter].startsWith(FINAL)) {
                if (states == null) {
                    throw new InCorrectFormatException("states should be defined before assigning the finals");
                }

                parseFinal(splitText[counter], states);
            } else if (splitText[counter].startsWith(TRANSITIONS)) {
                if (alphabet == null || states == null) {
                    throw new InCorrectFormatException("states and aphabet should be implentend first!");
                }

                List<String> expectedToBeTransitions = Arrays.asList(splitText.clone());
                expectedToBeTransitions = expectedToBeTransitions.subList(counter, expectedToBeTransitions.size());
                transitions = parseTransitions((Collection) expectedToBeTransitions, alphabet, states);
            }

            counter++;
        }

        if (entry == null || states == null || alphabet == null)
            throw new InCorrectFormatException("format is incorrect");

        return new Automaton(entry, states, alphabet);

    }

    private static Collection<Label> parseAlphabet(String alphabet) {
        alphabet = alphabet.replaceAll("\\s+", "");
        alphabet = alphabet.substring(8);

        String[] alphabetArray = alphabet.split("");

        return createLabelsFromString((Collection) Arrays.asList(alphabetArray));
    }

    private static Collection<State> parseState(String states) {
        states = states.replaceAll("\\s+", "");
        states = states.substring(7);

        String[] separatedStates = states.split(",");

        return createStatesFromString((Collection) Arrays.asList(separatedStates));
    }

    private static void parseFinal(String finals, Collection<State> states) {
        finals = finals.replaceAll("\\s+", "");
        finals = finals.substring(6);

        String[] separatedFinals = finals.split(",");

        createFinalsFromString(Arrays.asList(separatedFinals), states);
    }

    private static Collection<Transition> parseTransitions(Collection<String> transitions, Collection<Label> alphabet, Collection<State> states) {
        Collection<Transition> returnValue = new ArrayList<Transition>();
        int counter = 0;

        for (String transitionLine : transitions) {
            if (counter == 0) {
                counter++;
                continue;
            }

            transitionLine = transitionLine.replaceAll("\\s+", "");
            String[] transition = transitionLine.split("-->");

            // finding the corresponding labels and states
            State to = getStateFromCollection(transition[1], states);
            transition = transition[0].split(",");
            State from = getStateFromCollection(transition[0], states);
            Label transitionLabel = getLabelFromCollection(transition[1], alphabet);

            Transition trans = new Transition(from, to, transitionLabel);
            returnValue.add(trans);

            // adding the transition to the corresponding states
            try {
                from.addTransition(trans);
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        }

        return returnValue;
    }

    private static Collection<Label> createLabelsFromString(Collection<String> labels) {
        Collection<Label> returnValue = new ArrayList<Label>();

        for (String label : labels) {
            returnValue.add(new DefinedLabel(label));
        }

        return returnValue;
    }

    private static Collection<State> createStatesFromString(Collection<String> states) {
        Collection<State> returnValue = new ArrayList<State>();

        for (String state : states) {
            returnValue.add(new State(state));
        }

        return returnValue;
    }

    private static void createFinalsFromString(Collection<String> finals, Collection<State> states) {
        for (String aFinal : finals) {
            getStateFromCollection(aFinal, states).setFinal();
        }
    }

/*    private static Collection<Transition> createTransitionFromString(Collection<String> transitions) {
        Collection<Transition> returnValue = new ArrayList<Transition>();

        for (String transition : transitions) {
            returnValue.add(new (transition));
        }

        return returnValue;
    }*/

    private static State getStateFromCollection(final String stateName, Collection<State> states) {
        for (State state : states) {
            if (state.getStateName().equals(stateName)) {
                return state;
            }
        }

        return null;
    }

    private static Label getLabelFromCollection(String labelName, Collection<Label> labels) {
        for (Label label : labels) {
            if (labelName.equals("eps")) {
                return Epsilon.getInstance();
            } else {
                if (((DefinedLabel) label).definedLabel.equals(labelName)) return label;
            }
        }

        return null;
    }
}
