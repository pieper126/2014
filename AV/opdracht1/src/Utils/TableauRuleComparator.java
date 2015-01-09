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
                if (isNegation(o2) && isNegation(((Negation) o2).getSideA())) {
                    return 0;
                } else {
                    return 1;
                } // negation-implication rule and negation-disjunction rule
            } else if (isDisjunction(((Negation) o1).getSideA()) || isImplication(((Negation) o1).getSideA())) {
                if (isNegation(o2) && isNegation(((Negation) o2).getSideA())) {
                    return -1;
                } else if (isConjunction(o2) || (isNegation(o2) && isImplication(((Negation) o2).getSideA()) || isNegation(o2) && isDisjunction(((Negation) o2).getSideA()))) {
                    return 0;
                } else {
                    return 1;
                }// disjunction rule and implication rule
            } else if (isConjunction(((Negation) o1).getSideA())) {
                if (isDisjunction(o2) || isImplication(o2) || (isNegation(o2) && isConjunction(((Negation) o2).getSideA()))) {
                    return 0;
                } else {
                    return -1;
                }
            }
        } else if (isConjunction(o1)) { // conjuction rule

            // double negation rule
            if (isNegation(o2) && isNegation(((Negation) o2).getSideA())) {
                return -1;
                // negation-implication rule and negation-disjunction rule
            } else if (isConjunction(o2) || (isNegation(o2) && isImplication(((Negation) o2).getSideA()) || isNegation(o2) && isDisjunction(((Negation) o2).getSideA()))) {
                return 0;
                // disjunction rule and implication rule
            } else {
                return 1;
            }

        } else if (isDisjunction(o1)) { // disjunction rule

            // disjunction rule, negation-conjunction rule and implication rule
            if (isDisjunction(o2) || isImplication(o2) || (isNegation(o2) && isConjunction(((Negation) o2).getSideA()))) {
                return 0;
            } else if (isAbstractVariable(o2) || (isNegation(o2) && isAbstractVariable(((Negation) o2).getSideA()))) {
                return 1;
            } else {
                return -1;
            }

        } else if (o1.getClass().getTypeName().equals(IMPLICATION)) { // implication rule

            // disjunction rule, negation-conjunction rule and implication rule
            if (isDisjunction(o2) || isImplication(o2) || (isNegation(o2) && isConjunction(((Negation) o2).getSideA()))) {
                return 0;
            } else if (isAbstractVariable(o2) || (isNegation(o2) && isAbstractVariable(((Negation) o2).getSideA()))) {
                return 1;
            } else {
                return -1;
            }

        } else { // abstract variable

            // abstractvariable rule
            if (isAbstractVariable(o2) || (isNegation(o2) && isAbstractVariable(((Negation) o2).getSideA()))) {
                return 0;
            } else {
                return -1;
            }
        }
        return 0;
    }

}
