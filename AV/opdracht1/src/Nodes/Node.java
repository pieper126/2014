package Nodes;

public class Node {

    transient Equation currentEquation;

    public Node(Equation currentEquation) {
        this.currentEquation = currentEquation;
    }

    public Equation getCurrentEquation() {
        return currentEquation;
    }

    /**
     *
     * @param currentEquation
     */
    public void setCurrentEquation(Equation currentEquation) {
        this.currentEquation = currentEquation;
    }

    @Override
    public String toString() {
        return currentEquation.equation;
    }
}
