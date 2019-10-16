package org.ezvolve.core.evaluation;

import com.gargoylesoftware.base.testing.EqualsTester;
import org.junit.Test;
import static org.junit.Assert.*;

public final class FitnessTest {

    @Test
    public void testFitnessConstructors() {
        Fitness.of(100);
        Fitness.of(100.0);
    }

    @Test
    public void testToString() {
        assertEquals("500.0", Fitness.of(500).toString());
        assertEquals("100.0", Fitness.of(100).toString());
        assertEquals("5.0", Fitness.of(5).toString());
        assertEquals("0.1", Fitness.of(0.1).toString());
        assertEquals("0.0", Fitness.of(0.0).toString());
    }

    @Test
    public void testGetters() {

        Fitness fitnessA = Fitness.of(100);
        Fitness fitnessB = Fitness.of(200);
        Fitness fitnessC = Fitness.of(2000);

        assertEquals(100, fitnessA.intValue());
        assertEquals(200, fitnessB.intValue());
        assertEquals(2000, fitnessC.intValue());

        assertEquals(Double.valueOf(100.0),
                     Double.valueOf(fitnessA.doubleValue()));
        assertEquals(Double.valueOf(200.0),
                     Double.valueOf(fitnessB.doubleValue()));
        assertEquals(Double.valueOf(2000.0),
                     Double.valueOf(fitnessC.doubleValue()));

    }

    @Test
    public void testGettersWithPositiveDecimals() {

        Fitness fitnessA = Fitness.of(1.0);
        Fitness fitnessB = Fitness.of(2.25);
        Fitness fitnessC = Fitness.of(6.5);
        Fitness fitnessD = Fitness.of(9.75);

        assertEquals(1, fitnessA.intValue());

        assertEquals(Double.valueOf(1.0),
                     Double.valueOf(fitnessA.doubleValue()));
        assertEquals(Double.valueOf(2.25),
                     Double.valueOf(fitnessB.doubleValue()));
        assertEquals(Double.valueOf(6.5),
                     Double.valueOf(fitnessC.doubleValue()));
        assertEquals(Double.valueOf(9.75),
                     Double.valueOf(fitnessD.doubleValue()));

    }

    @Test
    public void testGettersWithBigValues() {

        Fitness fitnessA = Fitness.of(Integer.MAX_VALUE - 1);
        Fitness fitnessB = Fitness.of(Integer.MAX_VALUE);
        Fitness fitnessC = Fitness.of(Integer.MIN_VALUE);
        Fitness fitnessD = Fitness.of(Integer.MIN_VALUE + 1);

        assertEquals(2147483646, fitnessA.intValue());
        assertEquals(2147483647, fitnessB.intValue());
        assertEquals(-2147483648, fitnessC.intValue());
        assertEquals(-2147483647, fitnessD.intValue());

        assertEquals(Double.valueOf(2147483646.0),
                     Double.valueOf(fitnessA.doubleValue()));
        assertEquals(Double.valueOf(2147483647.0),
                     Double.valueOf(fitnessB.doubleValue()));
        assertEquals(Double.valueOf(-2147483648.0),
                     Double.valueOf(fitnessC.doubleValue()));
        assertEquals(Double.valueOf(-2147483647.0),
                     Double.valueOf(fitnessD.doubleValue()));

    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructionWithNaN1() {
        Fitness.of(Double.NaN);
    }

    @Test
    public void testConstructionWithNegativeInfinity0() {
        Fitness.of(Double.NEGATIVE_INFINITY);
    }

    @Test
    public void testConstructionWithNegativeInfinity1() {
        Fitness.of(Double.NEGATIVE_INFINITY);
    }

    @Test
    public void testConstructionWithPositiveInfinity() {
        assertEquals(Double.valueOf(Double.POSITIVE_INFINITY),
                     Double.valueOf(Fitness.of(Double.POSITIVE_INFINITY).doubleValue()));
        assertEquals(Double.valueOf(Double.NEGATIVE_INFINITY),
                     Double.valueOf(Fitness.of(Double.NEGATIVE_INFINITY).doubleValue()));
    }
    
    @Test
    public void testConstructionWithZeros() {
        assertEquals(Double.valueOf(0.0),
                     Double.valueOf(Fitness.of(0.0).doubleValue()));
        assertEquals(Double.valueOf(0.0),
                     Double.valueOf(Fitness.of(-0.0).doubleValue()));
    }

    @Test
    public void testIntegerOverflowNotTooFar() {

        Fitness fitness;

        // Exactly max value
        fitness = Fitness.of((double) Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, fitness.intValue());

        // Exactly min value
        fitness = Fitness.of((double) Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, fitness.intValue());

    }

    @Test
    public void testIntegerOverflowFails() {

        Fitness fitness;

        fitness = Fitness.of(((double) Integer.MAX_VALUE) + 1.0);
        fitness.doubleValue();
        try {
            int value = fitness.intValue();
            fail("returned " + value);
        } catch (ArithmeticException expected) {
        }

        fitness = Fitness.of(((double) Integer.MIN_VALUE) - 1.0);
        fitness.doubleValue();
        try {
            int value = fitness.intValue();
            fail("returned " + value);
        } catch (ArithmeticException expected) {
        }

        fitness = Fitness.of(0.5);
        fitness.doubleValue();
        try {
            int value = fitness.intValue();
            fail("returned " + value);
        } catch (ArithmeticException expected) {
        }

        fitness = Fitness.of(-0.5);
        fitness.doubleValue();
        try {
            int value = fitness.intValue();
            fail("returned " + value);
        } catch (ArithmeticException expected) {
        }

    }

    @Test
    public void testEquals() {
        Fitness a = Fitness.of(100); // original
        Fitness b = Fitness.of(100); // same
        Fitness c = Fitness.of(5);   // different
        Fitness d = null;             // subclass
        new EqualsTester(a, b, c, d);
    }

}
