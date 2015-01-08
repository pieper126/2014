/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Nodes.*;
import java.util.Comparator;
import static Utils.NodeTypenames.*;

/**
 *
 * @author stijn
 */
public class TableauRuleComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        // everything that uses the negation
        if (o1.getClass().getTypeName().equals(NEGATION)) {

            // double negation rule
            if (((Negation) o1).getSideA().getClass().getTypeName().equals(NEGATION)) {
                if (o2.getClass().getTypeName().equals(NEGATION) && ((Negation) o2).getSideA().getClass().getTypeName().equals(NEGATION)) {
                    return 1;
                } else {
                    return 0;
                } // negation-implication rule and negation-disjunction rule
            } else if (((Negation) o1).getSideA().getClass().getTypeName().equals(DISJUNCTION) || ((Negation) o1).getSideA().getClass().getTypeName().equals(IMPLICATION)) {
                if ((o2.getClass().getTypeName().equals(NEGATION) && ((Negation) o2).getSideA().getClass().getTypeName().equals(NEGATION))) {
                    return 1;
                } else if (o2.getClass().getTypeName().equals(CONJUNCTION) || (o2.getClass().getTypeName().equals(NEGATION) && ((Negation) o2).getSideA().getClass().getTypeName().equals(IMPLICATION)) || (o2.getClass().getTypeName().equals(NEGATION) && ((Negation) o2).getSideA().getClass().getTypeName().equals(DISJUNCTION))) {
                    return 0;
                } else {
                    return -1;
                }// disjunction rule and implication rule
            } else if (((Negation) o1).getSideA().getClass().getTypeName().equals(CONJUNCTION)) {
                if (o2.getClass().getTypeName().equals(DISJUNCTION) || o2.getClass().getTypeName().equals(IMPLICATION) || (o2.getClass().getTypeName().equals(NEGATION) && ((Negation) o2).getSideA().getClass().getTypeName().equals(CONJUNCTION))) {
                    return -1;
                } else {
                    return 0;
                }
            }
        } else if (o1.getClass().getTypeName().equals(CONJUNCTION)) { // conjuction rule

            // double negation rule
            if (o2.getClass().getTypeName().equals(NEGATION) && ((Negation) o2).getSideA().getClass().getTypeName().equals(NEGATION)) {
                return 1;
                // negation-implication rule and negation-disjunction rule
            } else if (o2.getClass().getTypeName().equals(CONJUNCTION) || (o2.getClass().getTypeName().equals(NEGATION) && ((Negation) o2).getSideA().getClass().getTypeName().equals(IMPLICATION)) || (o2.getClass().getTypeName().equals(NEGATION) && ((Negation) o2).getSideA().getClass().getTypeName().equals(DISJUNCTION))) {
                return 0;
                // disjunction rule and implication rule
            } else {
                return -1;
            }

        } else if (o1.getClass().getTypeName().equals(DISJUNCTION)) { // disjunction rule

            // disjunction rule, negation-conjunction rule and implication rule
            if (o2.getClass().getTypeName().equals(DISJUNCTION) || o2.getClass().getTypeName().equals(IMPLICATION) || (o2.getClass().getTypeName().equals(NEGATION) && ((Negation) o2).getSideA().getClass().getTypeName().equals(CONJUNCTION))) {
                return 0;
            } else if (o2.getClass().getTypeName().equals(ABSTRACTVARIABLE)) {
                return -1;
            } else {
                return 1;
            }

        } else if (o1.getClass().getTypeName().equals(IMPLICATION)) { // implication rule

            // disjunction rule, negation-conjunction rule and implication rule
            if (o2.getClass().getTypeName().equals(DISJUNCTION) || o2.getClass().getTypeName().equals(IMPLICATION) || (o2.getClass().getTypeName().equals(NEGATION) && ((Negation) o2).getSideA().getClass().getTypeName().equals(CONJUNCTION))) {
                return 0;
            } else if (o2.getClass().getTypeName().equals(ABSTRACTVARIABLE)) {
                return -1;
            } else {
                return 1;
            }

        } else { // abstract variable

            // abstractvariable rule
            if (o2.getClass().getTypeName().equals(ABSTRACTVARIABLE)) {
                return 0;
            } else {
                return 1;
            }
        }
        return 0;
    }

}
