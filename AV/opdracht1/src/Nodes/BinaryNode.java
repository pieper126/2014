/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nodes;

import com.sun.org.apache.bcel.internal.generic.AALOAD;

/**
 *
 * @author pieper126
 */
public class BinaryNode extends Node {
    protected Node SideA;
    protected Node SideB;
    
    public BinaryNode(Equation currentEquation) {
        super(currentEquation);
        String stringSideA = super.currentEquation.equation.substring(0, super.currentEquation.equation.indexOf(",") - 1);
        String stringSideB = super.currentEquation.equation.substring(super.currentEquation.equation.indexOf(",") + 1);
        
        SideA = Translater.Translater.Parse(stringSideA);
        SideB = Translater.Translater.Parse(stringSideB);
    }
    
}
