package Nodes;

public class negation extends MonoNode {

    public negation(Node sideA) {
        super(sideA);
    }

    @Override
    public String toString() {
        return "¬" + "(" + sideA.toString() + ")";
    }

}
