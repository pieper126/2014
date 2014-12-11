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
//            exit = console.readLine("Enter equation in infix: ");
//
//            Equation equation = new Equation(exit);
//
//            String whatToDo = "";
//            do {
//                whatToDo = console.readLine("enter a option: ");
//
//                switch (whatToDo) {
//                    case "help":
//                        System.out.print("\"normal\" for the normal truthtable \n \"simplified\" for the simplified truthtable \n \"exit\" to exit the application");
//                        break;
//                    case "normal":
//                        equation.printTruthTable();
//                        break;
//                    case "simplified":
//                        equation.printingSimplifiedTruthTables();
//                        break;
//                }
//            } while (!whatToDo.trim().toLowerCase().equals("exit"));
//        } while (!exit.trim().toLowerCase().equals("exit"));
        
        // TODO code application logic here
//        Equation test = new Equation("=( >(D,B), &( ~(A) ,C) )");
//        Equation test = new Equation(">(A,B)");
//        Equation test = new Equation("=(=(A,C),B)");
//        Equation test = new Equation("=(C,B)");
        Equation test = new Equation("|(|(|(|(|(A,B),C),D),E),F)");
//        Equation test = new Equation("A");
        
        
//>(A,B)
//=(A,B)
//&(A,B)
//|(A,B)


        System.err.println("asdsadsadadasd");
        System.out.println(test.toString());
        System.out.println("distinctvar \n");
        for (Node node : test.getDistinctVariable()) {
            System.out.println(node.toString() + "\n");
        }

        test.printTruthTable();

        test.printingSimplifiedTruthTables();
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
