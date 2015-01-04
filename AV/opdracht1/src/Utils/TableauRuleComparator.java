/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Nodes.*;
import java.util.Comparator;

/**
 *
 * @author stijn
 */
public class TableauRuleComparator implements Comparator<Node> {

    private final String negation = "Nodes.Negation";

    @Override
    public int compare(Node o1, Node o2) {
        if (o1.getClass().getTypeName().equals(negation)) {
            
            // double negation rule
            if (((Negation) o1).getSideA().getClass().getTypeName().equals(negation)) {
                if (o2.getClass().getTypeName().equals(negation) && ((Negation) o2).getSideA().getClass().getTypeName().equals(negation)) {
                    return 0;
                } else {
                    return -1;
                }
            }
            
            //
        }
    }

}
