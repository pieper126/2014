package Nodes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Equation {

    protected String equation;

    private Node mainNode;

    private ArrayList<Node> distinctVariables;
    
    private boolean[] truthTable;

    public Equation(String equation) {
        this.equation = equation;
        mainNode = Translater.Translater.Parse(equation);
        ArrayList<Node> totalVar = (ArrayList<Node>) mainNode.getDistinctVariable();
        distinctVariables = new ArrayList<>();

        for (Node var : totalVar) {
            if (!distinctVariables.contains(var)) {
                distinctVariables.add(var);
            }
        }
        
        variableValueAllocation();
        setDistinctVariable();
        
        truthTable = mainNode.getTruthValues();
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
        return mainNode != null ? equation + "\n" + mainNode.toString() : "";
    }

    public List<Node> getDistinctVariable() {
        return distinctVariables;
    }

    public void setDistinctVariable() {
        mainNode.setDistinctVariable(distinctVariables);
    }

    public void variableValueAllocation() {
        int n = distinctVariables.size();
        for (int i = 0; i != (1 << n); i++) {
            String s = Integer.toBinaryString(i);
            int j = 0;
            int sizeBooleanArray = (int) Math.pow(distinctVariables.size(), distinctVariables.size());
            boolean[] truthvalues = new boolean[sizeBooleanArray];
            while (s.length() != n) {
                s = '0' + s;
                truthvalues[j] = s.equals("1");
                j++;
            }
            ((AbstractVariable) distinctVariables.get(i)).setTruthValues(truthvalues);
        }
    }

    public void printTruthTable() {
        String line = "";
        for (int j = 0; j < distinctVariables.size(); j++) {
            line += ((AbstractVariable) distinctVariables.get(j)).var + " ";
        }
        line += equation + "\n";

        for (int i = 0; i < mainNode.getTruthValues().length; i++) {
            line = "";
            for (int j = 0; j < distinctVariables.size(); j++) {
                line += ((AbstractVariable) distinctVariables.get(j)).getTruthValues()[i] + " ";
            }
            line += truthTable[i] + "\n";

        }
    }

//    public void printTree(){
//        mainNode.prinTree();
//    }
}
