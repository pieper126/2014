package Nodes;

public class Negation extends MonoNode {

    public Negation(Node sideA) {
        super(sideA);
    }

    @Override
    public String NANDForm() {
        return "~&(" + sideA.NANDForm() + "," + sideA.NANDForm() + ")";
    }

    @Override
    public String toString() {
        return "¬" + "(" + sideA.toString() + ")";
    }

    @Override
    public boolean[] getTruthValues() {
        boolean[] input = sideA.getTruthValues();
        int length = input.length;

        boolean[] afterOperation = new boolean[length];
        for (int i = 0; i < length; i++) {
            afterOperation[i] = !input[i];
        }

        return afterOperation;
    }
}
