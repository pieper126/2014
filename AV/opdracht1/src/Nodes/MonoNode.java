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
public class MonoNode extends Node {

    protected Node sideA;

    public MonoNode(Node sideA) {
        this.sideA = sideA;
    }

    @Override
    public List<Node> getDistinctVariable() {
        ArrayList<Node> returnvalue = new ArrayList<Node>();

        // test if sideA is already a Abstract variable
        if (sideA.getClass().getTypeName().equals(AbstractVariable.class.getTypeName())) {
            if (!returnvalue.contains(sideA.toString())) {
                returnvalue.add(sideA);
            }
        } else {
            returnvalue.addAll((ArrayList<Node>) sideA.getDistinctVariable());
        }

        return returnvalue;
    }

    @Override
    public void setDistinctVariable(List<Node> distinctVars) {
        if (sideA.getClass().getTypeName().equals(AbstractVariable.class.getTypeName())) {
            for (Node distinctVar : distinctVars) {
                if (distinctVar.equals(sideA)) {
                    sideA = distinctVar;
                }
            }
        } else {
            setDistinctVariable(distinctVars);
        }
    }

    @Override
    public void printTree() {
    }

    @Override
    public boolean[] getTruthValues(){
        return null;
    }
}
