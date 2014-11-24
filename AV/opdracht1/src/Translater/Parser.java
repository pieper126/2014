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
        String TrimmedEquation = equation.trim();
        
        if (TrimmedEquation.length() == 1) {
            return new AbstractVariable(TrimmedEquation);
        }
        
        // takes the relevant operator from the equation
        String operator = TrimmedEquation.substring(0);

        // takes the childern from the given equation
        String childern = equation.substring(2, equation.length() - 1);
        Node returnValue = null;

        // decide what type of operant it is
        switch (operator) {
            case "~":
                returnValue = new negation(childern);
                break;
            case ">":
                returnValue = new implication(childern);
                break;
            case "=":
                returnValue = new BiImplication(childern);
                break;
            case "&":
                returnValue = new Conjunction(childern);
                break;
            case "|":
                returnValue = new disjuction(childern);
                break;       
        }

        return returnValue;
    }

}
