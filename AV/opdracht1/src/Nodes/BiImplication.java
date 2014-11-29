package Nodes;

public class BiImplication extends BinaryNode {

    public BiImplication(String currentEquation, Node sideA, Node sideB) {
        super(new Equation(currentEquation));
    }
    
}