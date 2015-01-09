/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author stijn
 */
public class TypeNodeChecker {

    public static boolean isNegation(Object object) {
        return object.getClass().getTypeName().equals(NodeTypenames.NEGATION);
    }

    public static boolean isImplication(Object object) {
        return object.getClass().getTypeName().equals(NodeTypenames.IMPLICATION);
    }

    public static boolean isBiImplication(Object object) {
        return object.getClass().getTypeName().equals(NodeTypenames.BIIMPLICATION);
    }

    public static boolean isConjunction(Object object) {
        return object.getClass().getTypeName().equals(NodeTypenames.CONJUNCTION);
    }

    public static boolean isDisjunction(Object object) {
        return object.getClass().getTypeName().equals(NodeTypenames.DISJUNCTION);
    }

    public static boolean isAbstractVariable(Object object) {
        return object.getClass().getTypeName().equals(NodeTypenames.ABSTRACTVARIABLE);
    }
}
