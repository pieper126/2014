/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Nodes.*;
import java.util.Comparator;
import static Utils.NodeTypenames.*;
import static Utils.TypeNodeChecker.*;

/**
 *
 * @author stijn
 */
public class TableauRuleComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        // everything that uses the negation
        if (isNegation(o1)) {

            // double negation rule
            if (isNegation(((Negation) o1).getSideA())) {

                return doubleNegationRule(o1, o2); // negation-implication rule and negation-disjunction rule

            } else if (isDisjunction(((Negation) o1).getSideA()) || isImplication(((Negation) o1).getSideA())) {

                return alphaRule(o1, o2);// disjunction rule and implication rule

            } else if (isConjunction(((Negation) o1).getSideA())) {

                return betaRule(o1, o2);

            } else if (isBiImplication(o1)) {

                return betaRule(o1, o2);

            } else {

                return abstractVariableRule(o1, o2);

            }
        } else if (isConjunction(o1)) { // conjuction rule

            return alphaRule(o1, o2);

        } else if (isDisjunction(o1)) { // disjunction rule

            return betaRule(o1, o2);

        } else if (isImplication(o1)) { // implication rule

            return betaRule(o1, o2);

        } else if (isBiImplication(o1)) {

            return betaRule(o1, o2);

        } else { // abstract variable

            return abstractVariableRule(o1, o2);

        }
    }

    private int alphaRule(Object o1, Object o2) {
        // double negation rule
        if (isNegation(o2) && isNegation(((Negation) o2).getSideA())) {
//            System.out.println(o1.toString() + "+" + o2.toString() + "=  1");
            return 1;
            // negation-implication rule and negation-disjunction rule
        } else if (isConjunction(o2) || (isNegation(o2) && isImplication(((Negation) o2).getSideA()) || isNegation(o2) && isDisjunction(((Negation) o2).getSideA()))) {
//            System.out.println(o1.toString() + "+" + o2.toString() + "=  0");
            return 0;
            // disjunction rule and implication rule
        } else {
//            System.out.println(o1.toString() + "+" + o2.toString() + "=  -1");
            return -1;
        }
    }

    private int betaRule(Object o1, Object o2) {
        // disjunction rule, negation-conjunction rule and implication rule
        if (isDisjunction(o2) || isImplication(o2) || isBiImplication(o2) || (isNegation(o2) && isConjunction(((Negation) o2).getSideA())) || (isNegation(o2) && isBiImplication(((Negation) o2).getSideA()))) {
//            System.out.println(o1.toString() + "+" + o2.toString() + "=  0");
            return 0;
        } else if (isAbstractVariable(o2) || (isNegation(o2) && isAbstractVariable(((Negation) o2).getSideA()))) {
//            System.out.println(o1.toString() + "+" + o2.toString() + "=  1");
            return -1;
        } else {
//            System.out.println(o1.toString() + "+" + o2.toString() + "=  -1");
            return 1;
        }
    }

    private int doubleNegationRule(Object o1, Object o2) {
        if (isNegation(o2) && isNegation(((Negation) o2).getSideA())) {
//            System.out.println(o1.toString() + "+" + o2.toString() + "=  0");
            return 0;
        } else {
//            System.out.println(o1.toString() + "+" + o2.toString() + "=  -1");
            return -1;
        }
    }

    private int abstractVariableRule(Object o1, Object o2) {
        // abstractvariable rule
        if (isAbstractVariable(o2) || (isNegation(o2) && isAbstractVariable(((Negation) o2).getSideA()))) {
//            System.out.println(o1.toString() + "+" + o2.toString() + "=  0");
            return 0;
        } else {
//            System.out.println(o1.toString() + "+" + o2.toString() + "=  1");
            return 1;
        }
    }
}
