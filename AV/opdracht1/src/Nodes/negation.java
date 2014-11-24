package Nodes;

public class negation extends Node {

    public negation(String equation) {
        super(new Equation(equation));
        SideA = new AbstractVariable(equation);
    }
    
}