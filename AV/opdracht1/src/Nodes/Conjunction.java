package Nodes;

public class Conjunction extends BinaryNode {

    public Conjunction(Node sideA, Node sideB) {
        super(sideA, sideB);
    }

    @Override
    public String toString() {
        return "(" + sideA.toString() + "∧" + sideB.toString() + ")";
    }
}
