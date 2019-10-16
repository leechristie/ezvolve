package org.ezvolve.core.stochastic;

import com.gargoylesoftware.base.testing.EqualsTester;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.Assert.*;
import org.junit.Test;

public final class ProbabilityTest {

    @Test
    public void testFactory() {
        assertEquals(0.0, Probability.of(0.0).doubleValue(), 0.0);
        assertEquals(0.5, Probability.of(0.5).doubleValue(), 0.0);
        assertEquals(1.0, Probability.of(1.0).doubleValue(), 0.0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFactoryLowerBound() {
        Probability.of(Math.nextAfter(0.0, Double.NEGATIVE_INFINITY));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFactoryUpperBound() {
        Probability.of(Math.nextAfter(1.0, Double.POSITIVE_INFINITY));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFactoryNaN() {
        Probability.of(Double.NaN);
    }

    @Test
    public void testToString() {
        assertEquals("P(0.0)", Probability.of(0.0).toString());
        assertEquals("P(0.5)", Probability.of(0.5).toString());
        assertEquals("P(1.0)", Probability.of(1.0).toString());
    }

    @Test
    public void testEquals() {
        Probability a = Probability.of(0.9); // original
        Probability b = Probability.of(0.9); // same
        Probability c = Probability.of(0.7); // different
        Probability d = null;                // subclass
        new EqualsTester(a, b, c, d);
    }

    @Test
    public void testNextDoubleExact() {
        Random r = ThreadLocalRandom.current();
        Probability one = Probability.of(1.0);
        for (int i = 0; i < 100000; i++) {
            if (one.nextBoolean(r) == false) {
                throw new AssertionError("one returned false");
            }
        }
        Probability zero = Probability.of(0.0);
        for (int i = 0; i < 100000; i++) {
            if (zero.nextBoolean(r) == true) {
                throw new AssertionError("zero returned true");
            }
        }
    }

    @Test
    public void testNextDoubleStochastic() {
        Random r = ThreadLocalRandom.current();
        Probability evens = Probability.of(0.5);
        int evensCounter = 0;
        for (int i = 0; i < 1000000; i++) {
            if (evens.nextBoolean(r)) {
                evensCounter++;
            }
        }
        assertEquals(0.5, evensCounter/1000000.0, 0.005);
        Probability pointSeven = Probability.of(0.7);
        int pointSevenCounter = 0;
        for (int i = 0; i < 1000000; i++) {
            if (pointSeven.nextBoolean(r)) {
                pointSevenCounter++;
            }
        }
        assertEquals(0.7, pointSevenCounter/1000000.0, 0.005);
    }

}