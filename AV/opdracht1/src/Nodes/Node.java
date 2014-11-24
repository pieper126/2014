package Nodes;

public class Node {

    private Equation currentEquation;
    
    protected Node SideA;

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

}
