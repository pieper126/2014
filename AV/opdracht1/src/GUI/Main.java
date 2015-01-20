/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Nodes.Equation;
import Nodes.Node;
import com.sun.glass.ui.SystemClipboard;
import java.io.Console;

/**
 *
 * @author pieper126
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
         to do:
         - biImplication fixen v
         - fixen disjunctive normal form
         - dubbele in simplified
         */
//        Console console = System.console();
//
//        String exit;
//        do {
//            exit = console.readLine("Enter equation in prefix: ");
//
//            Equation equation = new Equation(exit);
//
//            String whatToDo = "";
//            do {
//                whatToDo = console.readLine("\"1\" for the normal truthtable \n \"2\" for the simplified truthtable \n \"3\" for the disjunctie normal form \n \"4\" to check if a another equation is equal \n \"5\" to give the nand form of this equation \n \"6\" to check if ti's a tautology using tableau \n \"exit\" to exit the application \n"
//                        + "enter a option: ");
//
//                switch (whatToDo) {
//                    case "0":
//                        System.out.print("\"normal\" for the normal truthtable \n \"simplified\" for the simplified truthtable \n \"disjunctive normal form\" for the disjunctie normal form \n \"equality\" to check if a another equation is equal \n \"exit\" to exit the application");
//                        break;
//                    case "1":
//                        long timer = System.currentTimeMillis();
//                        equation.printTruthTable();
//                        timer = System.currentTimeMillis() - timer;
//                        System.out.println("time it took to complete: " + timer + "ms \n");
//                        break;
//                    case "2":
//                        timer = System.currentTimeMillis();
//                        equation.printingSimplifiedTruthTables();
//                        timer = System.currentTimeMillis() - timer;
//                        System.out.println("time it took to complete: " + timer + "ms \n");
//                        break;
//                    case "3":
//                        timer = System.currentTimeMillis();
//                        equation.printDisjunctiveNormalForm();
//                        timer = System.currentTimeMillis() - timer;
//                        System.out.println("time it took to complete: " + timer + "ms \n");
//                        break;
//                    case "4":
//                        String newEquation = console.readLine("Enter equation in prefix: ");
//                        timer = System.currentTimeMillis();
//                        try {
//                            if (equation.equals(new Equation(newEquation))) {
//                                System.out.println("they are equal");
//                            } else {
//                                System.out.println("they are not equal");
//                            }
//                        } catch (NullPointerException e) {
//                            System.out.println("run simplified first!");
//                        }
//                        timer = System.currentTimeMillis() - timer;
//                        System.out.println("time it took to complete: " + timer + "ms \n");
//                        break;
//                    case "5":
//                        timer = System.currentTimeMillis();
//                        equation.printNAND();
//                        timer = System.currentTimeMillis() - timer;
//                        System.out.println("time it took to complete: " + timer + "ms \n");
//                        break;
//                    case "6":
//                        timer = System.currentTimeMillis();
//                        try {
//                            if (equation.isATautology()) {
//                                System.out.println("this is a tautology");
//                            } else {
//                                System.out.println("this is not a tautology");
//                            }
//                        } catch (NullPointerException e) {
//                            System.out.println("run simplified first!");
//                        }
//                        timer = System.currentTimeMillis() - timer;
//                        System.out.println("time it took to complete: " + timer + "ms \n");
//                        break;
//                }
//            } while (!whatToDo.trim().toLowerCase().equals("exit"));
//        } while (!exit.trim().toLowerCase().equals("exit"));

        // TODO code application logic here
//        Equation test = new Equation("=( >(D,B), &( ~(A) ,C) )");
//        Equation test = new Equation("&(~(A),B)");
//        Equation test2 = new Equation("|(~(~(A)),~(B))");
//        Equation test = new Equation("=(=(A,C),B)");
//        Equation test = new Equation("=(C,B)");
//        Equation test = new Equation("|(|(|(|(|(|(|(|(|(A,B),C),D),E),F),G),H),I),J)");
//        Equation test = new Equation("A");
//        Equation test = new Equation(">(>(|(P,Q),R),|(>(P,R),>(Q,R)))");
//        Equation test2 = new Equation("~(~(>(A,B)))");
        Equation test = new Equation("=(>(A,B),>(A,B))");
//>(A,B)
//=(A,B)
//&(A,B)
//|(A,B)
        test.printTruthTable();

        System.out.println(test.isATautology());

//        test.printNAND();
//        test2.printTruthTable();
//
//        test.printingSimplifiedTruthTables();
//
//        test.printDisjunctiveNormalForm();
//
//        System.out.println(test.equals(test2));
    }

    public static void test() {
        int n = 4;
        for (int i = 0; i != (1 << n); i++) {
            String s = Integer.toBinaryString(i);
            String out = "";
            int j = 0;
            int sizeBooleanArray = (int) Math.pow(n, n);
            boolean[] truthvalues = new boolean[sizeBooleanArray];
            while (s.length() != n) {
                s = '0' + s;
            }

            for (int k = 0; k < n; k++) {

            }
            System.out.println(s);
        }
    }
}
