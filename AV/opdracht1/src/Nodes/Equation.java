package Nodes;

import Utils.AbstractVarComparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Equation {

    protected String equation;

    private Node mainNode;

    private ArrayList<Node> distinctVariables;

    private ArrayList<Node> comparedDistinctVariables;

    private boolean[] truthTable;

    private ArrayList<ArrayList<Boolean>> simplifiedTruthTable;

    private int sizeBooleanArray;

    private Node DisjunctiveForm;

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

        comparedDistinctVariables = (ArrayList<Node>) distinctVariables.clone();
        Collections.sort(comparedDistinctVariables, new AbstractVarComparator());

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
        sizeBooleanArray = 1 << n;
        int j = 0;

        boolean[][] truthvalues = new boolean[distinctVariables.size()][sizeBooleanArray];

        for (int i = 0; i != (1 << n); i++) {
            String s = Integer.toBinaryString(i);

            while (s.length() != n) {
                s = '0' + s;
            }

            for (int k = 0; k < n; k++) {
                try {
                    truthvalues[k][j] = '1' == s.charAt(k);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                    System.err.println(e.getCause());
                }
            }

            j++;
        }

        for (int i = 0; i < n; i++) {
            distinctVariables.get(i).setTruthValues(truthvalues[i]);
        }
    }

    public void printTruthTable() {
        System.out.print("truthTable: \n");
        String line = "";
        for (int j = 0; j < distinctVariables.size(); j++) {
            line += ((AbstractVariable) comparedDistinctVariables.get(j)).var + " ";
        }
        line += mainNode.toString() + "\n";
        System.out.print(line);

        for (int i = 0; i < mainNode.getTruthValues().length; i++) {
            line = "";
            for (int j = 0; j < distinctVariables.size(); j++) {
                line += ((AbstractVariable) distinctVariables.get(j)).getTruthValues()[i] == false ? "0 " : "1 ";
            }
            line += truthTable[i] == false ? "0 \n" : "1 \n";
            System.out.print(line);
        }

        System.out.println("------------end of truthTable--------------");
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
        // zooi om vormen naar per truthtTableLine inplaats van per distinctVar
        ArrayList<ArrayList<Boolean>> newRun = new ArrayList<>();
        ArrayList<Integer> notToBeShownAgain = new ArrayList<Integer>();

        for (int i = 0; i < input.size(); i++) {
            boolean noDifferenceFound = true;
            for (int k = 0; k < input.size(); k++) {
                boolean difference = false;
                int differenceLocation = -1;
                int differenceLine = -1;
                boolean moreThenOneDifference = false;
                for (int j = 0; j < distinctVariables.size(); j++) {
                    if (input.get(i).get(j) != (input.get(k).get(j)) && input.get(i).get(distinctVariables.size()) == input.get(k).get(distinctVariables.size())) {
                        if (difference) {
                            moreThenOneDifference = true;
                            break;
                        } else {
                            difference = true;
                            noDifferenceFound = false;
                            differenceLocation = j;
                        }
                    }
                }

                if (!moreThenOneDifference && difference) {
                    ArrayList<Boolean> simplified = new ArrayList<>();
                    notToBeShownAgain.add(k);
                    notToBeShownAgain.add(i);

                    for (int j = 0; j < distinctVariables.size() + 1; j++) {
                        try {
                            simplified.add(input.get(i).get(j));
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                            System.err.println(e.toString());
                        }
                    }

                    simplified.set(differenceLocation, null);

                    if (!newRun.contains(simplified)) {
                        newRun.add(simplified);
                    }
                }

            }

            if (noDifferenceFound && !notToBeShownAgain.contains(i)) {
                ArrayList<Boolean> noDifferenceLine = new ArrayList<>();
                for (int j = 0; j < distinctVariables.size() + 1; j++) {
                    try {
                        noDifferenceLine.add(input.get(i).get(j));
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        System.err.println(e.toString());
                    }
                }
                output.add(noDifferenceLine);
            }
        }

        if (newRun.isEmpty()) {
            output.addAll(input);
            simplifiedTruthTable = output;
            String line = "";

            for (int j = 0; j < distinctVariables.size(); j++) {
                line += ((AbstractVariable) comparedDistinctVariables.get(j)).var + " ";
            }
            line += mainNode.toString() + "\n";
            System.out.print(line);

            for (int j = 0; j < output.size(); j++) {
                line = "";
                for (int i = 0; i < distinctVariables.size() + 1; i++) {
                    try {
                        if (output.get(j).get(i) == null) {
                            line += "* ";
                        } else if (output.get(j).get(i) == false) {
                            line += "0 ";
                        } else if (output.get(j).get(i) == true) {
                            line += "1 ";
                        }
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                System.out.println(line);
            }

            System.out.println("------------end of truthTable--------------");
        } else {
            printingSimplifiedTruthTables(newRun, output);
        }
    }

    public void printDisjunctiveNormalForm() {
        String outputLine = "";
        for (ArrayList<Boolean> line : simplifiedTruthTable) {
            if (line.get(line.size() - 1) == true) {
                outputLine += "(";

                for (int i = 0; i < line.size() - 1; i++) {
                    if (line.get(i) == null) {
                        // do nothing
                    } else if (line.get(i) == false) {
                        outputLine += "¬" + comparedDistinctVariables.get(i).toString() + "/\\";
                    } else if (line.get(i) == true) {
                        outputLine += comparedDistinctVariables.get(i).toString() + "/\\";
                    }
                }

                if (outputLine.endsWith("/\\")) {
                    outputLine = outputLine.substring(0, outputLine.length() - 2);
                }

                outputLine += ")\\/";
            }
        }

        if (outputLine.endsWith("\\/")) {
            outputLine = outputLine.substring(0, outputLine.length() - 2);
        }

        System.out.println(outputLine);
    }
    
    public void printNAND(){
        System.out.println(mainNode.NANDForm()); 
    }

    @Override
    public boolean equals(Object obj) {
        Equation toCompareTo = (Equation) obj;

        if (toCompareTo.comparedDistinctVariables.equals(this.comparedDistinctVariables)) {
            if (Arrays.equals(toCompareTo.truthTable, this.truthTable)) {
                return true;
            }
//            if (toCompareTo.truthTable.length == this.truthTable.length) {
//
//                for (int i = 0; i < toCompareTo.truthTable.length; i++) {
//                    
//                }
//            }
        }

        return false;
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
