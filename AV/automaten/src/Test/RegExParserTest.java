package Test;

import Automaton.Automaton;
import Parser.*;

import static org.junit.Assert.*;

public class RegExParserTest {

    @org.junit.Test
    public void testParse() throws Exception {
        // test automaton SingleCharAutomaton
        Automaton automatonTest = RegExParser.Parse("a");
        String shouldFail = "ab";
        String shouldSucceed = "a";

        assertTrue(automatonTest.isStringAccepted(shouldSucceed));
        assertFalse(automatonTest.isStringAccepted(shouldFail));

        // test automaton dot
        automatonTest = RegExParser.Parse(".(a,b)");
        shouldFail = "ab";
        shouldSucceed = "axb";
        String shouldFail1 = "axxb";
        String shouldFail2 = "axc";

        assertTrue(automatonTest.isStringAccepted(shouldSucceed));
        assertFalse(automatonTest.isStringAccepted(shouldFail));
        assertFalse(automatonTest.isStringAccepted(shouldFail1));
        assertFalse(automatonTest.isStringAccepted(shouldFail2));

        automatonTest = RegExParser.Parse(".(.(a,b),c)");
        shouldFail = "abc";
        shouldSucceed = "axbxc";
        shouldFail1 = "axxb";
        shouldFail2 = "axc";

        assertTrue(automatonTest.isStringAccepted(shouldSucceed));
        assertFalse(automatonTest.isStringAccepted(shouldFail));
        assertFalse(automatonTest.isStringAccepted(shouldFail1));
        assertFalse(automatonTest.isStringAccepted(shouldFail2));

        // test automaton alternation
        automatonTest = RegExParser.Parse("|(a,b)");
        shouldFail = "abc";
        shouldSucceed = "a";
        String shouldSucceed1 = "b";
        shouldFail1 = "axxb";
        shouldFail2 = "axc";

        assertTrue(automatonTest.isStringAccepted(shouldSucceed));
        assertTrue(automatonTest.isStringAccepted(shouldSucceed1));
        assertFalse(automatonTest.isStringAccepted(shouldFail));
        assertFalse(automatonTest.isStringAccepted(shouldFail1));
        assertFalse(automatonTest.isStringAccepted(shouldFail2));

        automatonTest = RegExParser.Parse("|(a,.(c,b))");
        shouldFail = "";
        shouldSucceed = "a";
        shouldSucceed1 = "cxb";
        shouldFail1 = "cxa";
        shouldFail2 = "axb";

        assertTrue(automatonTest.isStringAccepted(shouldSucceed));
        assertTrue(automatonTest.isStringAccepted(shouldSucceed1));
        assertFalse(automatonTest.isStringAccepted(shouldFail));
        assertFalse(automatonTest.isStringAccepted(shouldFail1));
        assertFalse(automatonTest.isStringAccepted(shouldFail2));

        //test automaton star
        automatonTest = RegExParser.Parse("*(a)");
        shouldFail = "ab";
        shouldSucceed = "a";
        shouldSucceed1 = "";
        String shouldSucceed2 = "aaaaaaaaaa";
        shouldFail1 = "axxb";
        shouldFail2 = "axc";

        assertTrue(automatonTest.isStringAccepted(shouldSucceed));
        assertTrue(automatonTest.isStringAccepted(shouldSucceed1));
        assertTrue(automatonTest.isStringAccepted(shouldSucceed2));
        assertFalse(automatonTest.isStringAccepted(shouldFail));
        assertFalse(automatonTest.isStringAccepted(shouldFail1));
        assertFalse(automatonTest.isStringAccepted(shouldFail2));

        automatonTest = RegExParser.Parse("|(b,*(a))");
        shouldFail = "ab";
        shouldSucceed = "a";
        shouldSucceed1 = "";
        shouldSucceed2 = "aaaaaaaaaa";
        String shouldSucceed3 = "b";
        shouldFail1 = "axxb";
        shouldFail2 = "axc";

        assertTrue(automatonTest.isStringAccepted(shouldSucceed));
        assertTrue(automatonTest.isStringAccepted(shouldSucceed1));
        assertTrue(automatonTest.isStringAccepted(shouldSucceed2));
        assertTrue(automatonTest.isStringAccepted(shouldSucceed3));
        assertFalse(automatonTest.isStringAccepted(shouldFail));
        assertFalse(automatonTest.isStringAccepted(shouldFail1));
        assertFalse(automatonTest.isStringAccepted(shouldFail2));
    }
}