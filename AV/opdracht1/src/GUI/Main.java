/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Nodes.AbstractVariable;
import Nodes.Equation;
import Nodes.Node;

/**
 *
 * @author pieper126
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        Equation test = new Equation("=( >(D,B), &( ~(A) ,C) )");
        Equation test = new Equation(">(A,B)");
//        Equation test = new Equation("=(=(A,C),B)");
//        Equation test = new Equation("A");

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
