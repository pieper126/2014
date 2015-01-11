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
public class MonoNode extends PropasitionalLogicNode {

    protected PropasitionalLogicNode sideA;

    public MonoNode(PropasitionalLogicNode sideA) {
        this.sideA = sideA;
    }

    public PropasitionalLogicNode getSideA() {
        return sideA;
    }

    @Override
    public List<PropasitionalLogicNode> getDistinctVariable() {
        ArrayList<PropasitionalLogicNode> returnvalue = new ArrayList<PropasitionalLogicNode>();

        // test if sideA is already a Abstract variable
        if (sideA.getClass().getTypeName().equals(AbstractVariable.class.getTypeName())) {
            if (!returnvalue.contains(sideA.toString())) {
                returnvalue.add(sideA);
            }
        } else {
            returnvalue.addAll((ArrayList<PropasitionalLogicNode>) sideA.getDistinctVariable());
        }

        return returnvalue;
    }

    @Override
    public void setDistinctVariable(List<PropasitionalLogicNode> distinctVars) {
        if (sideA.getClass().getTypeName().equals(AbstractVariable.class.getTypeName())) {
            for (PropasitionalLogicNode distinctVar : distinctVars) {
                if (distinctVar.equals(sideA)) {
                    sideA = distinctVar;
                }
            }
        } else {
            sideA.setDistinctVariable(distinctVars);
        }
    }

    @Override
    public void printTree() {
    }

    @Override
    public boolean[] getTruthValues() {
        return null;
    }

    @Override
    public void setTruthValues(boolean[] truthValues) {
        sideA.setTruthValues(truthValues);
    }

    @Override
    public String NANDForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
