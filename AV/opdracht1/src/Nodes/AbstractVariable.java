package Nodes;

public class AbstractVariable extends Node {
    
    public String var;

    public AbstractVariable(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return var;
    }
}