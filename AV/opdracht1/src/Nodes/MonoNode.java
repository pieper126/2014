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
public class MonoNode extends Node {
    protected Node SideA;
    
    public MonoNode(Equation currentEquation) {
        super(currentEquation);
        SideA = new AbstractVariable(super.currentEquation.equation);
    }
    
}
