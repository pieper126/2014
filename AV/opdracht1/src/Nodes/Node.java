package Nodes;

import java.util.List;

public abstract class Node {

    public abstract List<Node> getDistinctVariable();

    public abstract void printTree();

    public abstract void setDistinctVariable(List<Node> distinctVars);
    
    public abstract boolean[] getTruthValues();
    
    public abstract void setTruthValues(boolean[] truthValues);
    
    public abstract String NANDForm();
}
