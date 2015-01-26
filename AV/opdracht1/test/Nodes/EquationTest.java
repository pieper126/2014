/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nodes;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import junit.framework.Test;
import org.junit.Before;

/**
 *
 * @author stijn
 */
public class EquationTest {

    Equation biImplication;
    Equation implication;
    Equation doubleNegation;
    Equation negationBiImplication;
    Equation negationImplication;
    Equation disjunction;
    Equation conjunction;
    Equation negation;
    Equation lotOfDistinctVars;

    @Before
    public void setUp() {
        biImplication = new Equation("=(|(A,B),|(A,B))");
        implication = new Equation(">(A,B)");
        doubleNegation = new Equation("~(~(=(A,B)))");
        negationBiImplication = new Equation("~(=(A,B))");
        negationImplication = new Equation("~(>(A,B))");
        disjunction = new Equation("|(A,B)");
        conjunction = new Equation("&(A,B)");
        negation = new Equation("~(A)");
        lotOfDistinctVars = new Equation("|(|(|(|(|(|(|(|(|(A,B),C),D),E),F),G),H),I),J)");
    }

    /**
     * Test of getDistinctVariable method, of class Equation.
     */
    @Test
    public void testGetDistinctVariable() {
        for (Node distinctVariable : lotOfDistinctVars.getDistinctVariable()) {
            System.out.println(distinctVariable.toString());
        }

    }

    /**
     * Test of setDistinctVariable method, of class Equation.
     */
    @Test
    public void testSetDistinctVariable() {

    }

    /**
     * Test of variableValueAllocation method, of class Equation.
     */
    @Test
    public void testVariableValueAllocation() {

    }

    /**
     * Test of printTruthTable method, of class Equation.
     */
    @Test
    public void testPrintTruthTable() {

    }

    /**
     * Test of printingSimplifiedTruthTables method, of class Equation.
     */
    @Test
    public void testPrintingSimplifiedTruthTables() {

    }

    /**
     * Test of printDisjunctiveNormalForm method, of class Equation.
     */
    @Test
    public void testPrintDisjunctiveNormalForm() {

    }

    /**
     * Test of printNAND method, of class Equation.
     */
    @Test
    public void testPrintNAND() {

    }

    /**
     * Test of isATautology method, of class Equation.
     */
    @Test
    public void testIsATautology() {

    }

    /**
     * Test of equals method, of class Equation.
     */
    @Test
    public void testEquals() {

    }

    /**
     * Test of toString method, of class Equation.
     */
    @Test
    public void testToString() {

    }

}
