package Nodes;

public class negation extends MonoNode {

    public negation(String equation) {
        super(new Equation(equation));
        SideA = new Node(new Equation(equation));
    }
    
}