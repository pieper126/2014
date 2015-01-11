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

    @Test
    public void testComparator() {
        ArrayList<Node> nodesToBeordend = new ArrayList<>();
        ArrayList<Node> nodeHowItShouldBeOrdend = new ArrayList<>();

        nodesToBeordend.add(Parser.Parse("~(A)"));
        nodesToBeordend.add(Parser.Parse("~(B)"));
        nodesToBeordend.add(Parser.Parse("~(~(A))"));
        nodesToBeordend.add(Parser.Parse("A"));
        nodesToBeordend.add(Parser.Parse("~(>(A,B))"));
        nodesToBeordend.add(Parser.Parse("~(|(A,B))"));
        nodesToBeordend.add(Parser.Parse("~(~(>(A,B)))"));
        nodesToBeordend.add(Parser.Parse("|(A,B)"));
        nodesToBeordend.add(Parser.Parse("&(A,B)"));
        nodesToBeordend.add(Parser.Parse("A"));
        nodesToBeordend.add(Parser.Parse("~(~(B))"));
        nodesToBeordend.add(Parser.Parse(">(A,B)"));

        nodeHowItShouldBeOrdend.add(Parser.Parse("~(~(A))"));
        nodeHowItShouldBeOrdend.add(Parser.Parse("~(~(B))"));
        nodeHowItShouldBeOrdend.add(Parser.Parse("~(~(>(A,B)))"));
        nodeHowItShouldBeOrdend.add(Parser.Parse("~(>(A,B))"));
        nodeHowItShouldBeOrdend.add(Parser.Parse("&(A,B)"));
        nodeHowItShouldBeOrdend.add(Parser.Parse(">(A,B)"));
        nodeHowItShouldBeOrdend.add(Parser.Parse("~(A)"));
        nodeHowItShouldBeOrdend.add(Parser.Parse("~(B)"));
        nodeHowItShouldBeOrdend.add(Parser.Parse("A"));
        nodeHowItShouldBeOrdend.add(Parser.Parse("A"));

        nodesToBeordend.sort(new TableauRuleComparator());

        assertArrayEquals(nodeHowItShouldBeOrdend.toArray(), nodesToBeordend.toArray());

    }

}
