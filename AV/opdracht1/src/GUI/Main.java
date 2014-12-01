/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

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
//        Equation test = new Equation("=( >(A,B), |( ~(A) ,B) )");
////        Equation test = new Equation("=(A,B)");
////        Equation test = new Equation("A");
//        System.out.println(test.toString());
//        for (Node node : test.getDistinctVariable()) {
//            System.out.println(node.toString() + "\n");
//        }
        
        test();
    }

    public static void test() {
        int n = 2;
        for (int i = 0; i != (1 << n); i++) {
            String s = Integer.toBinaryString(i);
            while (s.length() != n) {
                
            }
            System.out.println(s);
        }
    }
}
