package Nodes;

import java.util.List;

public class AbstractVariable extends Node {

    public String var;
    
    private boolean[] truthValues;

    public AbstractVariable(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return var;
    }

    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }

    @Override
    public void printTree() {
        
    }

    @Override
    public List<Node> getDistinctVariable() {
        return null;
    }

    @Override
    public void setDistinctVariable(List<Node> distinctVars) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setTruthValues(boolean[] truthvalues){
        this.truthValues = truthvalues;
    }
    
    @Override
    public boolean[] getTruthValues(){
        return truthValues;
    }
}
