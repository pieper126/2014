/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Nodes.AbstractVariable;
import Nodes.Node;
import java.util.Comparator;

/**
 *
 * @author stijn
 */
public class AbstractVarComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        return ((AbstractVariable) o1).var.compareTo(((AbstractVariable) o2).var);
    }

}

/*public class CustomComparator implements Comparator<MyObject> {
    @Override
    public int compare(MyObject o1, MyObject o2) {
        return o1.getStartDate().compareTo(o2.getStartDate());
    }
}*/
