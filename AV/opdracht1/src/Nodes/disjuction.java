package Nodes;

public class disjuction extends BinaryNode {

    public disjuction(Node sideA, Node sideB) {
        super(sideA, sideB);
    }

    @Override
    public String toString() {
        return "(" + sideA.toString() + "∨" + sideB.toString() + ")";
    }
}
