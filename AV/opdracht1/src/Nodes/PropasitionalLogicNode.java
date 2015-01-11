/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nodes;

import java.util.List;

/**
 *
 * @author stijn
 */
public abstract class PropasitionalLogicNode extends Node {

    public abstract List<PropasitionalLogicNode> getDistinctVariable();

    public abstract void printTree();

    public abstract void setDistinctVariable(List<PropasitionalLogicNode> distinctVars);

    public abstract boolean[] getTruthValues();

    public abstract void setTruthValues(boolean[] truthValues);

    public abstract String NANDForm();
}
