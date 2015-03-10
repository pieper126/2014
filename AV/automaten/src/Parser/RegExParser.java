package Parser;

import Automaton.*;
import Automaton.StandardRegExAutomatons.SingleCharAutomaton;

/**
 * Created by pieper126 on 08-03-15.
 */
public class RegExParser {

    public static Automaton Parse(String equation) {
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
                    returnValue = Parse(children, 0, "", TypeNodes.Implication);
                } else {
                    returnValue = new Implication(Parse(children.substring(0, children.indexOf(","))), Parse(children.substring(children.indexOf(",") + 1, children.length())));
                }
                break;
            case "|":
                if (hasOperator(children)) {
                    returnValue = Parse(children, 0, "", TypeNodes.BiImplication);
                } else {
                    returnValue = new BiImplication(Parse(children.substring(0, children.indexOf(","))), Parse(children.substring(children.indexOf(",") + 1, children.length())));
                }
                break;
            case "*":
                if (hasOperator(children)) {
                    returnValue = Parse(children, 0, "", TypeNodes.Conjuction);
                } else {
                    returnValue = new Conjunction(Parse(children.substring(0, children.indexOf(","))), Parse(children.substring(children.indexOf(",") + 1, children.length())));
                }
                break;
        }

        return returnValue;
    }

    protected static Node Parse(String equation, int amountOfOperants, String buffer, TypeNodes type) {
        String trimmedEquation = equation.trim();

        // takes the childern from the given equation
        String children = equation.substring(2, equation.length());
        Node returnValue = null;

        // decide what type of operant it is
        if (hasOperator(trimmedEquation)) {
            if (type != TypeNodes.Negation) {
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
                case BiImplication:
                    Node A = Parse(sideAInTheNode);
                    Node B = Parse(sideBInTheNode);
                    returnValue = new BiImplication(A, B);
                    break;
                case Conjuction:
                    A = Parse(sideAInTheNode);
                    B = Parse(sideBInTheNode);
                    returnValue = new Conjunction(A, B);
                    break;
                case Disjunction:
                    A = Parse(sideAInTheNode);
                    B = Parse(sideBInTheNode);
                    returnValue = new Disjuction(A, B);
                    break;
                case Implication:
                    A = Parse(sideAInTheNode);
                    B = Parse(sideBInTheNode);
                    returnValue = new Implication(A, B);
                    break;
                case Negation:
                    A = Parse(sideAInTheNode + "," + sideBInTheNode /*+ ")"*/);
                    returnValue = new Negation(A);
            }
        }

        return returnValue;
    }

    private static boolean hasOperator(String input) {
        input = input.substring(0, 1);
        return input.contains("_") || input.contains(".") || input.contains("|") || input.contains("*");
    }
}
