/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Translater;

import Nodes.*;

/**
 *
 * @author pieper126
 */
public class Parser {

    protected static Node Parse(String equation) {
        String trimmedEquation = equation.trim();

        String buffer;

        if (trimmedEquation.length() == 1) {
            return new AbstractVariable(trimmedEquation);
        }

        // takes the relevant operator from the equation
        String operator = trimmedEquation.substring(0, 1);

        // takes the childern from the given equation
        String children = equation.substring(2, equation.length() - 1);
        Node returnValue = null;

        // decide what type of operant it is
        switch (operator) {
            case "~":
                if (hasOperator(children)) {
                    buffer = trimmedEquation.substring(0, 2);
                    returnValue = Parse(children, 1, buffer, TypeNodes.Negation);
                } else {
                    returnValue = new negation(children);
                }
                break;
            case ">":
                if (hasOperator(children)) {
                    buffer = trimmedEquation.substring(0, 2);
                    returnValue = Parse(children, 1, buffer);
                } else {
                    returnValue = new implication(children);
                }
                break;
            case "=":
                if (hasOperator(children)) {
                    buffer = trimmedEquation.substring(0, 2);
                    returnValue = Parse(children, 1, buffer);
                } else {
                    returnValue = new BiImplication(children);
                }
                break;
            case "&":
                if (hasOperator(children)) {
                    buffer = trimmedEquation.substring(0, 2);
                    returnValue = Parse(children, 1, buffer);
                } else {
                    returnValue = new Conjunction(children);
                }
                break;
            case "|":
                if (hasOperator(children)) {
                    buffer = trimmedEquation.substring(0, 2);
                    returnValue = Parse(children, 1, buffer);
                } else {
                    returnValue = new disjuction(children);
                }
                break;
        }

        return returnValue;
    }

    protected static Node Parse(String equation, int amountOfOperants, String buffer, TypeNodes type) {
        String trimmedEquation = equation.trim();

        // takes the childern from the given equation
        String children = equation.substring(2, equation.length() - 1);
        Node returnValue = null;

        // decide what type of operant it is
        if (hasOperator(trimmedEquation)) {
            buffer = trimmedEquation.substring(0, 2);
            returnValue = Parse(children, ++amountOfOperants, buffer, type);
        } else {
            int locationSecondLastParenthesis = 0;

            for (int i = 0; i < amountOfOperants; i++) {
                locationSecondLastParenthesis = equation.indexOf(")", locationSecondLastParenthesis);
            }

            int locationMainComa = equation.indexOf(",", locationSecondLastParenthesis) == -1 ? equation.length() - 1 : equation.indexOf(",", locationSecondLastParenthesis);

            String sideAInTheNode = buffer + equation.substring(0, locationMainComa);
            String sideBInTheNode = equation.substring(locationMainComa + 1);

            switch (type) {
                case BiImplication:
                    Node A = Parse(sideAInTheNode);
                    Node B = Parse(sideBInTheNode);
                    returnValue = new BiImplication(trimmedEquation, A, B);
                    break;
                case Conjuction:
                    Node A = Parse(sideAInTheNode);
                    Node B = Parse(sideBInTheNode);
                    returnValue = new Conjunction(trimmedEquation, A, B);
                    break;
                case Disjunction:
                    Node A = Parse(sideAInTheNode);
                    Node B = Parse(sideBInTheNode);
                    returnValue = new disjuction(trimmedEquation, A, B);
                    break;
                case Implication:
                    Node A = Parse(sideAInTheNode);
                    Node B = Parse(sideBInTheNode);
                    returnValue = new implication(trimmedEquation, A, B);
                    break;
                case Negation:
                    Node A = Parse(sideAInTheNode);
                    returnValue = new negation(trimmedEquation, A);
            }
        }

        return returnValue;
    }

    private static boolean hasOperator(String input) {
        input = input.substring(0);
        return input.contains("~") || input.contains(">") || input.contains("=") || input.contains("&") || input.contains("|");
    }
}
