package Nodes;

public class implication extends BinaryNode {

    public implication(Node sideA, Node sideB) {
        super(sideA, sideB);
    }

    @Override
    public String toString() {
        return "(" + sideA.toString() + "⇒" + sideB.toString() + ")";
    }
}
