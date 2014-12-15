package Nodes;

public class disjuction extends BinaryNode {

    public disjuction(Node sideA, Node sideB) {
        super(sideA, sideB);
    }

    @Override
    public String toString() {
        return "(" + sideA.toString() + "∨ \\/" + sideB.toString() + ")";
    }

    @Override
    public boolean[] getTruthValues() {
        boolean[] inputA = sideA.getTruthValues();
        boolean[] inputB = sideB.getTruthValues();
        int length = inputA.length;

        boolean[] afterOperation = new boolean[length];
        for (int i = 0; i < length; i++) {
            afterOperation[i] = (inputA[i] == true && inputB[i] == false) || (inputA[i] == false && inputB[i] == true) || (inputA[i] == true && inputB[i] == true);
        }

        return afterOperation;
    }
}
