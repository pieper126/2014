package Nodes;

import static java.util.Collections.unmodifiableList;
import java.util.List;

public class Equation {

    protected String equation;

    private List<Node> equationInNodes;

    public Equation(String equation) {
        this.equation = equation;
    }

    public List<Node> getEquationInNodes() {
        return unmodifiableList(equationInNodes);
    }

    public String getEquation() {
        return this.equation;
    }

    /**
     *
     * @param equation
     */
    public void setEquation(String equation) {
        this.equation = equation;
    }

    @Override
    public String toString() {
        return equation;
    }

}
