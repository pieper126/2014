/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.               
 */
package Nodes;

/**
 *
 * @author pieper126
 */
public class BinaryNode extends Node {
    protected Node sideA;
    protected Node sideB;
    
    public BinaryNode(Equation currentEquation, Node sideA, Node sideB) {
        super(currentEquation);
//        String stringSideA = super.currentEquation.equation.substring(0, super.currentEquation.equation.indexOf(",") - 1); // meerder comma's mogelijk
//        String stringSideB = super.currentEquation.equation.substring(super.currentEquation.equation.indexOf(",") + 1);
        
        this.sideA = sideA;
        this.sideB = sideB
    }
    
}
