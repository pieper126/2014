/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.               
 */
package Nodes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pieper126
 */
public class BinaryNode extends Node {

    protected Node sideA;
    protected Node sideB;

    public BinaryNode(Node sideA, Node sideB) {
        this.sideA = sideA;
        this.sideB = sideB;
    }

    /**
     * gets side B from the node
     *
     * @return
     */
    public Node getSideB() {
        return sideB;
    }

    /**
     * gets side A from the node
     *
     * @return
     */
    public Node getSideA() {
        return sideA;
    }

    /**
     *  gets the distinct variables from their children
     * @return 
     */
    @Override
    public List<Node> getDistinctVariable() {
        ArrayList<Node> returnvalue = new ArrayList<Node>();

        // test if sideA is already a Abstract variable
        if (sideA.getClass().getTypeName().equals(AbstractVariable.class.getTypeName())) {
            if (!returnvalue.contains(sideA)) {
                returnvalue.add(sideA);
            }
        } else {
            returnvalue.addAll((ArrayList<Node>) sideA.getDistinctVariable());
        }

        // test if sideB is already a Abstract variable
        if (sideB.getClass().getTypeName().equals(AbstractVariable.class.getTypeName())) {
            if (!returnvalue.contains(sideB)) {
                returnvalue.add(sideB);
            }
        } else {
            returnvalue.addAll((ArrayList<Node>) sideB.getDistinctVariable());
        }

        return returnvalue;
    }

    /**
     * sets the distinct variables from their children
     * @param distinctVars 
     */
    @Override
    public void setDistinctVariable(List<Node> distinctVars) {
        if (sideA.getClass().getTypeName().equals(AbstractVariable.class.getTypeName())) {
            for (Node distinctVar : distinctVars) {
                if (((AbstractVariable) distinctVar).var.equals(((AbstractVariable) sideA).var)) {
                    sideA = distinctVar;
                    System.err.println(toString() + distinctVar.toString());
                }
            }
        } else {
            sideA.setDistinctVariable(distinctVars);
        }

        // test if sideB is already a Abstract variable
        if (sideB.getClass().getTypeName().equals(AbstractVariable.class.getTypeName())) {
            for (Node distinctVar : distinctVars) {
                if (((AbstractVariable) distinctVar).var.equals(((AbstractVariable) sideB).var)) {
                    sideB = distinctVar;
                    System.err.println(toString() + distinctVar.toString());
                }
            }
        } else {
            sideB.setDistinctVariable(distinctVars);
        }
    }

    @Override
    public void printTree() {
    }

    /**
     * gets the current truthtValue
     * @return 
     */
    @Override
    public boolean[] getTruthValues() {
        return null;
    }

    /**
     * set the truth values
     * @param truthValues 
     */
    @Override
    public void setTruthValues(boolean[] truthValues) {
        sideA.setTruthValues(truthValues);
        sideB.setTruthValues(truthValues);
    }

    /**
     * gets the NANDForm
     * @return 
     */
    @Override
    public String NANDForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
