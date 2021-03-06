package Nodes;

public class Conjunction extends BinaryNode {

    public Conjunction(PropasitionalLogicNode sideA, PropasitionalLogicNode sideB) {
        super(sideA, sideB);
    }

    @Override
    public String NANDForm() {
        return "((" + sideA.NANDForm() + "|" + sideB.NANDForm() + ") | (" + sideA.NANDForm() + "|" + sideB.NANDForm() + ")" + ")";
    }

    @Override
    public String toString() {
        return "(" + sideA.toString() + "∧ /\\" + sideB.toString() + ")";
    }

    @Override
    public boolean[] getTruthValues() {
        boolean[] inputA = sideA.getTruthValues();
        boolean[] inputB = sideB.getTruthValues();
        int length = inputA.length;

        boolean[] afterOperation = new boolean[length];
        for (int i = 0; i < length; i++) {
            afterOperation[i] = inputA[i] && inputB[i];
        }

        return afterOperation;
    }

}
