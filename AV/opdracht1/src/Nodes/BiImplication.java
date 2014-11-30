package Nodes;

public class BiImplication extends BinaryNode {

    public BiImplication(Node sideA, Node sideB) {
        super(sideA, sideB);
    }

    @Override
    public String toString() {
        return "("+ sideA.toString() + "⇔" + sideB.toString() + ")";
    }
}