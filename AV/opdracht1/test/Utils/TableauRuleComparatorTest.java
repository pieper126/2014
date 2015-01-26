/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Nodes.Node;
import Translater.Parser;
import java.util.ArrayList;
import junit.framework.Assert;

/**
 *
 * @author pieper126
 */
public class TableauRuleComparatorTest {

    public void setUp() {
    }

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

        Assert.assertEquals(nodeHowItShouldBeOrdend.toArray(), nodesToBeordend.toArray());

    }

}
