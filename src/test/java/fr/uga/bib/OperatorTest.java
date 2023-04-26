package fr.uga.bib;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class OperatorTest {

    @Test
    public void GTHTest() {
        assertTrue("GTH test", Operator.GTH.test(20, 10));
        assertFalse("GTH test", Operator.GTH.test(15, 15));
        assertFalse("GTH test", Operator.GTH.test(10, 20));
    }

    @Test
    public void LTHTest() {
        assertFalse("LTH test", Operator.LTH.test(20L, 10L));
        assertFalse("LTH test", Operator.LTH.test(15L, 15L));
        assertTrue("LTH test", Operator.LTH.test(10L, 20L));
    }

    @Test
    public void GEQTest() {
        assertTrue("GEQ test", Operator.GEQ.test(20.0, 10.0));
        assertTrue("GEQ test", Operator.GEQ.test(15.0, 15.0));
        assertFalse("GEq test", Operator.GEQ.test(10.0, 20.0));
    }

    @Test
    public void LEQTest() {
        assertFalse("LEQ test", Operator.LEQ.test(20.0f, 10.0f));
        assertTrue("LEQ test", Operator.LEQ.test(15.0f, 15.0f));
        assertTrue("LEQ test", Operator.LEQ.test(10.0f, 20.0f));
    }

    @Test
    public void EQTest() {
        assertTrue("EQ test", Operator.EQ.test("equal", "equal"));
        assertFalse("EQ test", Operator.EQ.test("not equal", "different"));
    }

    @Test
    public void DIFTest() {
        assertTrue("GTH test", Operator.DIF.test('n', 'd'));
        assertFalse("GTH test", Operator.DIF.test('=', '='));
    }
}
