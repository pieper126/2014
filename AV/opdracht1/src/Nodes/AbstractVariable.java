package Nodes;

public class AbstractVariable extends Node {

    public AbstractVariable(String currentEquation) {
        super(new Equation(currentEquation));
    }
    
}