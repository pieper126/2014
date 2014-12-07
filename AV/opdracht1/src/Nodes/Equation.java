package Nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Equation {

    protected String equation;

    private Node mainNode;

    private ArrayList<Node> distinctVariables;

    private boolean[] truthTable;

    private int sizeBooleanArray;

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

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public List<Node> getDistinctVariable() {
        return distinctVariables;
    }

    public void setDistinctVariable() {
        mainNode.setDistinctVariable(distinctVariables);
    }

    public void variableValueAllocation() {
        int n = distinctVariables.size();
        sizeBooleanArray = (int) Math.pow(n, 2);
        int j = 0;

        boolean[][] truthvalues = new boolean[distinctVariables.size()][sizeBooleanArray];

        for (int i = 0; i != (1 << n); i++) {
            String s = Integer.toBinaryString(i);
            String out = "";

            while (s.length() != n) {
                s = '0' + s;
            }

            for (int k = 0; k < n; k++) {
                truthvalues[k][j] = '1' == s.charAt(k);
            }

            j++;
        }

        for (int i = 0; i < n; i++) {
            distinctVariables.get(i).setTruthValues(truthvalues[i]);
        }
    }

    public void printTruthTable() {
        System.out.println("truthTable: \n");
        String line = "";
        for (int j = 0; j < distinctVariables.size(); j++) {
            line += ((AbstractVariable) distinctVariables.get(j)).var + " ";
        }
        line += mainNode.toString() + "\n";
        System.out.println(line);

        for (int i = 0; i < mainNode.getTruthValues().length; i++) {
            line = "";
            for (int j = 0; j < distinctVariables.size(); j++) {
                line += ((AbstractVariable) distinctVariables.get(j)).getTruthValues()[i] + " ";
            }
            line += truthTable[i] + "\n";
            System.out.println(line);
        }
    }

    public void printingSimplifiedTruthTables() {
        ArrayList<ArrayList<Boolean>> input = new ArrayList<>();
        ArrayList<ArrayList<Boolean>> output = new ArrayList<>();

        for (int i = 0; i < sizeBooleanArray; i++) {
            
            ArrayList<Boolean> truthTableLine = new ArrayList<>();
            
            for (Node distinctVariable : distinctVariables) {
                truthTableLine.add(distinctVariable.getTruthValues()[i]);
            }
            
            truthTableLine.add(truthTable[i]);
            input.add(truthTableLine);
        }

//        for (int i = 0; i < distinctVariables.size(); i++) {
//            ArrayList<Boolean> distinctTruthTable = new ArrayList<>();
//            boolean[] distinctTruthTableArray = distinctVariables.get(i).getTruthValues();
//
//            for (int j = 0; j < sizeBooleanArray; j++) {
//
//                distinctTruthTable.add(distinctTruthTableArray[j]);
//            }
//            input.add(distinctTruthTable);
//        }
//
//        ArrayList<Boolean> truthTableList = new ArrayList<>();
//        for (int j = 0; j < sizeBooleanArray; j++) {
//            truthTableList.add(truthTable[j]);
//        }
//        input.add(truthTableList);

        printingSimplifiedTruthTables(input, output);
    }

    private void printingSimplifiedTruthTables(ArrayList<ArrayList<Boolean>> input, ArrayList<ArrayList<Boolean>> output) {
        ArrayList<ArrayList<Boolean>> newRun = new ArrayList<>();
        ArrayList<Integer> notToBeShownAgain = new ArrayList<Integer>();

        for (int i = 0; i < input.size(); i++) {
            boolean noDifferenceFound = true;
            for (int k = 1; k < input.size(); k++) {
                boolean difference = false;
                int differenceLocation = -1;
                int differenceLine = -1;
                boolean moreThenOneDifference = false;
                for (int j = 0; j < distinctVariables.size(); j++) {
                    if (input.get(j).get(i) != (input.get(j).get(k)) && input.get(distinctVariables.size()).get(i) == input.get(distinctVariables.size()).get(k)) {
                        if (difference) {
                            moreThenOneDifference = true;
                            break;
                        } else {
                            difference = true;
                            differenceLocation = j;
                        }
                    }
                }

                if (!moreThenOneDifference && difference) {
                    ArrayList<Boolean> simplified = new ArrayList<>();
                    notToBeShownAgain.add(k);

                    for (int j = 0; j < distinctVariables.size() + 1; j++) {
                        simplified.add(input.get(j).get(i));
                    }

                    simplified.set(differenceLocation, null);

                    newRun.add(simplified);
                }

            }

            if (noDifferenceFound && !notToBeShownAgain.contains(i)) {
                ArrayList<Boolean> noDifferenceLine = new ArrayList<>();
                for (int j = 0; j < input.size(); j++) {
                    noDifferenceLine.add(input.get(j).get(i));
                }
                output.add(noDifferenceLine);
            }
        }

        if (newRun.isEmpty()) {
            for (int j = 0; j < output.size(); j++) {
                String line = "";
                for (int i = 0; i < distinctVariables.size() + 1; i++) {
                    line += output.get(j).get(i) == null ? " * " : output.get(j).get(i).toString() + " ";
                }
                System.out.println(line);
            }

        } else {
            printingSimplifiedTruthTables(newRun, output);
        }
    }

    @Override
    public String toString() {
        return mainNode != null ? equation + "\n" + mainNode.toString() : "";
    }

//    public void printTree(){
//        mainNode.prinTree();
//    }
//    optimalisatie begin bovenaan en werk naar beneden
//    match gevonden word deze behandeld in de volgende doorloping
}
