/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Nodes.Negation;
import Nodes.Node;
import Translater.Parser;
import com.sun.javafx.scene.control.TableColumnComparatorBase;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pieper126
 */
public class TableauRuleComparatorTest {

    @Before
    public void setUp() {
    }

    /**
     * Test of compare method, of class TableauRuleComparator.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        Node o1 = null;
        Node o2 = null;
        TableauRuleComparator instance = new TableauRuleComparator();
        int expResult = 0;
        int result = instance.compare(o1, o2);
        assertEquals(expResult, result);
    }

    @Test
    public void testComparator() {
        ArrayList<Node> nodesToBeordend = new ArrayList<>();
        ArrayList<Node> nodeHowItShouldBeOrdend = new ArrayList<>();

        Parser parser = new Parser();

        nodesToBeordend.add(parser.Parse("~(A)"));
        nodesToBeordend.add(parser.Parse("~(B)"));
        nodesToBeordend.add(parser.Parse("~(~(A))"));
        nodesToBeordend.add(parser.Parse("A"));
        nodesToBeordend.add(parser.Parse("~(>(A,B))"));
        nodesToBeordend.add(parser.Parse("~(~(>(A,B)))"));
        nodesToBeordend.add(parser.Parse("&(A,B)"));
        nodesToBeordend.add(parser.Parse("A"));
        nodesToBeordend.add(parser.Parse("~(~(B))"));
        nodesToBeordend.add(parser.Parse(">(A,B)"));

        nodeHowItShouldBeOrdend.add(parser.Parse("~(~(A))"));
        nodeHowItShouldBeOrdend.add(parser.Parse("~(~(B))"));
        nodesToBeordend.add(parser.Parse("~(~(>(A,B)))"));
        nodeHowItShouldBeOrdend.add(parser.Parse("~(>(A,B))"));
        nodeHowItShouldBeOrdend.add(parser.Parse("&(A,B)"));
        nodeHowItShouldBeOrdend.add(parser.Parse(">(A,B)"));
        nodeHowItShouldBeOrdend.add(parser.Parse("~(A)"));
        nodeHowItShouldBeOrdend.add(parser.Parse("~(B)"));
        nodeHowItShouldBeOrdend.add(parser.Parse("A"));
        nodeHowItShouldBeOrdend.add(parser.Parse("A"));

        nodesToBeordend.sort(new TableauRuleComparator());

        assertArrayEquals(nodeHowItShouldBeOrdend.toArray(), nodesToBeordend.toArray());

    }

}
