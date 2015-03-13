package Parser;

import Automaton.*;
import Automaton.StandardRegExAutomatons.SingleCharAutomaton;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by pieper126 on 08-03-15.
 */
public class RegExParser {

    public static Automaton Parse(String equation) throws InvalidArgumentException {
        // should not be needed
        String trimmedEquation = equation.trim();
        trimmedEquation = trimmedEquation.replaceAll("\\s+", "");

        // checks if the equation is not just a single abstract variable
        if (trimmedEquation.length() == 1) {
            return new SingleCharAutomaton(trimmedEquation);
        }

        // takes the relevant operator from the equation
        String operator = trimmedEquation.substring(0, 1);

        // takes the childern from the given equation
        String children = equation.substring(2, equation.length() - 1);
        Automaton returnValue = null;

        // decide what type of operant it is
        switch (operator) {
            case "_":
                // to ask
//                if (hasOperator(children)) {
//                    returnValue = Parse(children, -1, "", TypeNodes.Negation);
//                } else {
//                    returnValue = new SingleCharAutomaton(children);
//                    ((SingleCharAutomaton)returnValue).addingTransition(new Transition());
//                    returnValue = new Negation(new AbstractVariable(children));
//                }
                break;
            case ".":
                if (hasOperator(children)) {
//                    Automaton innerAutomaton = Parse(children, 0, "", RegExType.dot);
//                    State state = new State("s");
//                    Entry entryPoint = new Entry(state);
//                    Collection<State> states = innerAutomaton.states;
//
//                    Transition transition = new Transition(state, innerAutomaton.entry.entryPoint, DotLabel.getInstance());
//                    state.addTransition(transition);
//
//                    states.add(state);
//
//                    returnValue = new Automaton(entryPoint, states, innerAutomaton.alphabet);
                    returnValue = Parse(children, 0, "", RegExType.dot);
                } else {
                    returnValue = createDotAutomaton(Parse(children.substring(0, children.indexOf(","))), Parse(children.substring(children.indexOf(",") + 1, children.length())));
                }
                break;
            case "|":
                if (hasOperator(children)) {
                    returnValue = Parse(children, 0, "", RegExType.alternation);
                } else {
                    returnValue = createAlternationAutomaton(Parse(children.substring(0, children.indexOf(","))), Parse(children.substring(children.indexOf(",") + 1, children.length())));
                }
                break;
            case "*":
                if (hasOperator(children)) {
                    returnValue = Parse(children, 0, "", RegExType.star);
                } else {
                    returnValue = createStarAutomaton((Parse(children)));
                }
                break;
        }

        return returnValue;
    }

    protected static Automaton Parse(String equation, int amountOfOperants, String buffer, RegExType type) throws InvalidArgumentException{
        String trimmedEquation = equation.trim();

        // takes the childern from the given equation
        String children = equation.substring(2, equation.length());
        Automaton returnValue = null;

        // decide what type of operant it is
        if (hasOperator(trimmedEquation)) {
            if (type != RegExType.eps) {
                ++amountOfOperants;
            }

            buffer += trimmedEquation.substring(0, 2);

            returnValue = Parse(children, amountOfOperants, buffer, type);

        } else {
            int locationSecondLastParenthesis = 0;

            for (int i = 0; i < amountOfOperants; i++) {
                locationSecondLastParenthesis = equation.indexOf(")", ++locationSecondLastParenthesis);
            }

            int locationMainComa = equation.indexOf(",", locationSecondLastParenthesis) == -1 ? equation.length() /*- 1*/ : equation.indexOf(",", locationSecondLastParenthesis);

            String sideAInTheNode = buffer + equation.substring(0, locationMainComa);

            String sideBInTheNode = "";

            if (locationMainComa == equation.length()) {

            } else {
                sideBInTheNode = equation.substring(locationMainComa + 1);
            }

            switch (type) {
                case dot:
                    Automaton A = Parse(sideAInTheNode);
                    Automaton B = Parse(sideBInTheNode);

                    returnValue = createDotAutomaton(A, B);
                    break;
                case alternation:
                    A = Parse(sideAInTheNode);
                    B = Parse(sideBInTheNode);

                    returnValue = createAlternationAutomaton(A, B);
                    break;
                case eps:
//                    try {
//                        A = Parse(sideAInTheNode);
//                    } catch (InvalidArgumentException e) {
//                        e.printStackTrace();
//                    }
//                    returnValue = new Implication(A, B);
//                    break;
                case star:
                    A = Parse(sideAInTheNode);

                    returnValue = createStarAutomaton(A);
            }
        }

        return returnValue;
    }

    private static boolean hasOperator(String input) {
        input = input.substring(0, 1);
        return input.contains("_") || input.contains(".") || input.contains("|") || input.contains("*");
    }

    /**
     * Creates a Automan that simulates the dot functionality of regex
     *
     * @param children
     * @return
     */
    private static Automaton createDotAutomaton(String children) {
        // creating the Entry
        State s1 = new State("s1");
        Entry entry = new Entry(s1);

        // creating the necessary labels
        DefinedLabel labelEntryToS2 = new DefinedLabel(children.substring(0, children.indexOf(",")));
        DefinedLabel labelEntryToS3 = new DefinedLabel(children.substring(children.indexOf(",") + 1, children.length()));

        // creating the necessary states
        State s2 = new State(labelEntryToS2.definedLabel);
        State s3 = new State(labelEntryToS3.definedLabel);

        // creating the necessary transitions
        Transition transitionEntryToS2 = new Transition(s1, s2, DotLabel.getInstance());
        Transition transitionEntryToS3 = new Transition(s1, s3, DotLabel.getInstance());

        // adding the transitions to the entry point
        try {
            s1.addTransition(transitionEntryToS2);
            s1.addTransition(transitionEntryToS3);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

        // creating the states
        Collection<State> states = new ArrayList<>();
        states.add(s1);
        states.add(s2);
        states.add(s3);

        // creating the alphabet
        Collection<Label> alphabet = new ArrayList<>();
        alphabet.add(labelEntryToS2);
        alphabet.add(labelEntryToS3);

        // creating finals
        Collection<State> finals = new ArrayList<>();
        finals.add(s3);

        return new Automaton(entry, states, alphabet, finals);
    }

    private static Automaton createDotAutomaton(Automaton sideA, Automaton sideB) {
        Entry entry = sideA.entry;

        Collection<State> states = sideA.states;
        states.addAll(sideB.states);

        HashSet<Label> alphabet = new HashSet<>(sideA.alphabet);
        alphabet.addAll(sideB.alphabet);

        Collection<State> finals = sideB.finals;

        // making transitions so finals can be mapped to the entry point of sideB
        for (State aFinal : sideA.finals) {
            Transition transitionFromAToB = new Transition(aFinal, sideB.entry.entryPoint, DotLabel.getInstance());

            try {
                aFinal.addTransition(transitionFromAToB);
                aFinal.unFinal();
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        }

        return new Automaton(entry, states, alphabet, finals);
    }

    private static Automaton createAlternationAutomaton(String children) {
        // creating the Entry
        State s1 = new State("s1");
        Entry entry = new Entry(s1);

        // creating the necessary labels
        DefinedLabel labelEntryToS2 = new DefinedLabel(children.substring(0, children.indexOf(",")));
        DefinedLabel labelEntryToS3 = new DefinedLabel(children.substring(children.indexOf(",") + 1, children.length()));

        // creating the necessary states
        State s2 = new State(labelEntryToS2.definedLabel);
        State s3 = new State(labelEntryToS3.definedLabel);

        // creating the necessary transitions
        Transition transitionEntryToS2 = new Transition(s1, s2, Epsilon.getInstance());
        Transition transitionEntryToS3 = new Transition(s1, s3, Epsilon.getInstance());

        // adding the transitions to the entry point
        try {
            s1.addTransition(transitionEntryToS2);
            s1.addTransition(transitionEntryToS3);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

        // creating the states
        Collection<State> states = new ArrayList<>();
        states.add(s1);
        states.add(s2);
        states.add(s3);

        // creating the alphabet
        Collection<Label> alphabet = new ArrayList<>();
        alphabet.add(labelEntryToS2);
        alphabet.add(labelEntryToS3);

        // creating finals
        Collection<State> finals = new ArrayList<>();
        finals.add(s3);

        return new Automaton(entry, states, alphabet, finals);
    }

    private static Automaton createAlternationAutomaton(Automaton sideA, Automaton sideB) {
        // create entry point
        State state = new State("s");
        Entry entry = new Entry(state);

        // link entry point to the entry point of the given automatons, so the alternation function can be added.
        Transition transitionToA = new Transition(state, sideA.entry.entryPoint, Epsilon.getInstance());
        Transition transitionToB = new Transition(state, sideB.entry.entryPoint, Epsilon.getInstance());
        try {
            state.addTransition(transitionToA);
            state.addTransition(transitionToB);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

        // set states
        Collection<State> states = sideA.states;
        states.addAll(sideB.states);
        states.add(state);

        // set alphabet
        HashSet<Label> alphabet = new HashSet<>(sideA.alphabet);
        alphabet.addAll(sideB.alphabet);

        // set finals
        Collection<State> finals = new LinkedList<>();
        finals.addAll(sideA.finals);
        finals.addAll(sideB.finals);

        return new Automaton(entry, states, alphabet, finals);
    }

    private static Automaton createStarAutomaton(Automaton sideA) {
        // create entry point
        State state = new State("s");
        Entry entry = new Entry(state);

        // link entry point to the entry point of the given automaton
        Transition transition = new Transition(state, sideA.entry.entryPoint, Epsilon.getInstance());
        try {
            state.addTransition(transition);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

        // set states
        Collection<State> states = sideA.states;
        states.add(state);

        // set alphabet
        HashSet<Label> alphabet = new HashSet<>(sideA.alphabet);

        // set finals
        Collection<State> finals = sideA.finals;

        // making transitions so finals can be mapped to the entry point of sideB
        for (State aFinal : sideA.finals) {
            Transition transitionFromAToB = new Transition(aFinal, sideA.entry.entryPoint, Epsilon.getInstance());

            try {
                aFinal.addTransition(transitionFromAToB);
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        }

        return new Automaton(entry, states, alphabet, finals);
    }
}
