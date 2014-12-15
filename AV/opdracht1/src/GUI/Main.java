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
//                whatToDo = console.readLine("\"normal\" for the normal truthtable \n \"simplified\" for the simplified truthtable \n \"disjunctive normal form\" for the disjunctie normal form \n \"equality\" to check if a another equation is equal \n \"exit\" to exit the application \n"
//                        + "enter a option: ");
//
//                switch (whatToDo) {
//                    case "help":
//                        System.out.print("\"normal\" for the normal truthtable \n \"simplified\" for the simplified truthtable \n \"disjunctive normal form\" for the disjunctie normal form \n \"equality\" to check if a another equation is equal \n \"exit\" to exit the application");
//                        break;
//                    case "normal":
//                        long timer = System.currentTimeMillis();
//                        equation.printTruthTable();
//                        timer = System.currentTimeMillis() - timer;
//                        System.out.println("time it took to complete: " + timer + "ms \n");
//                        break;
//                    case "simplified":
//                        timer = System.currentTimeMillis();
//                        equation.printingSimplifiedTruthTables();
//                        timer = System.currentTimeMillis() - timer;
//                        System.out.println("time it took to complete: " + timer + "ms \n");
//                        break;
//                    case "disjunctive normal form":
//                        timer = System.currentTimeMillis();
//                        equation.printDisjunctiveNormalForm();
//                        timer = System.currentTimeMillis() - timer;
//                        System.out.println("time it took to complete: " + timer + "ms \n");
//                        break;
//                    case "equality":
//                        String newEquation = console.readLine("Enter equation in prefix: ");                        
//                        timer = System.currentTimeMillis();
//                        try{
//                        if(equation.equals(new Equation(newEquation))){
//                            System.out.println("they are equal");
//                        } else{
//                            System.out.println("they are not equal");
//                        }
//                        } catch (NullPointerException e){
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
        Equation test = new Equation("|(~(A),B)");
        Equation test2 = new Equation(">(A,B)");
//        Equation test = new Equation("=(=(A,C),B)");
//        Equation test = new Equation("=(C,B)");
//        Equation test = new Equation("|(|(|(|(|(|(|(|(A,B),C),D),E),F),G),H),I)");
//        Equation test = new Equation("A");

//>(A,B)
//=(A,B)
//&(A,B)
//|(A,B)
        test.printTruthTable();
        test2.printTruthTable();

        test.printingSimplifiedTruthTables();
        
        test.printDisjunctiveNormalForm();
        
        System.out.println(test.equals(test2));
        
        
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
