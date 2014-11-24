package Nodes;

import static java.util.Collections.unmodifiableList;
import java.util.List;

public class Equation {

    protected String equation;

    private Node mainNode;

    public Equation(String equation) {
        this.equation = equation;
        mainNode = Translater.Translater.Parse(equation);
    }

    public Node GetMainNode() {
        return mainNode;
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
        return equation + "\n" + mainNode.toString();
    }

}
