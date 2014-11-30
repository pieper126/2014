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
    protected Node sideA;
    
    public MonoNode(Node sideA) {
        this.sideA = sideA;
    }
    
}
