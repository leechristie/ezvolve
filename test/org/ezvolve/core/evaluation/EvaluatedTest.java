package org.ezvolve.core.evaluation;

import com.gargoylesoftware.base.testing.EqualsTester;
import org.ezvolve.core.encoding.BitString;
import org.junit.Test;
import static org.junit.Assert.*;

public final class EvaluatedTest {

    @Test
    public void testCreateEvaluated() {

        Evaluated<BitString> ec1 = Evaluated.of(BitString.of(0, 0, 0),
                                Fitness.of(0.0));
        Evaluated<BitString> ec2 = Evaluated.of(BitString.of(0, 1, 0, 0, 1),
                                Fitness.of(200.0));
        Evaluated<String> ec3 = Evaluated.of("Hello World",
                                Fitness.of(0.0));
        Evaluated<Integer> ec4 = Evaluated.of(42,
                                Fitness.of(0.0));

        assertEquals(BitString.of(0, 0, 0), ec1.candidate());
        assertEquals(Fitness.of(0.0), ec1.fitness());

        assertEquals(BitString.of(0, 1, 0, 0, 1), ec2.candidate());
        assertEquals(Fitness.of(200.0), ec2.fitness());

        assertEquals("Hello World", ec3.candidate());
        assertEquals(Fitness.of(0.0), ec3.fitness());

        assertEquals(Integer.valueOf(42), ec4.candidate());
        assertEquals(Fitness.of(0.0), ec4.fitness());

    }

    @Test(expected=NullPointerException.class)
    public void testNullCandidate() {
        Evaluated.of(null, Fitness.of(0));
    }

    @Test(expected=NullPointerException.class)
    public void testNullFitness() {
        Evaluated.of(BitString.of(0, 0, 0), null);
    }

    @Test
    public void testEquals() {
        Evaluated<String> a =
                Evaluated.of("Hello", Fitness.of(100)); // original
        Evaluated<String> b =
                Evaluated.of("Hello", Fitness.of(100)); // same
        Evaluated<Integer> c =
                Evaluated.of(42, Fitness.of(1));        // different
        Evaluated<String> d = null;                      // subclass
        new EqualsTester(a, b, c, d);
    }

    @Test
    public void testToString() {
        BitString bitStringA = BitString.of(0, 1, 1, 1, 0, 0, 1, 0, 1);
        BitString bitStringB = BitString.of(1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0);
        Fitness fitnessA = Fitness.of(500);
        Fitness fitnessB = Fitness.of(10);
        Evaluated<BitString> aa
                = Evaluated.of(bitStringA, fitnessA);
        Evaluated<BitString> ab
                = Evaluated.of(bitStringA, fitnessB);
        Evaluated<BitString> ba
                = Evaluated.of(bitStringB, fitnessA);
        Evaluated<BitString> bb
                = Evaluated.of(bitStringB, fitnessB);
        assertTrue(aa.toString().contains(bitStringA.toString()));
        assertFalse(aa.toString().contains(bitStringB.toString()));
        assertTrue(aa.toString().contains(fitnessA.toString()));
        assertFalse(aa.toString().contains(fitnessB.toString()));
        assertTrue(ab.toString().contains(bitStringA.toString()));
        assertFalse(ab.toString().contains(bitStringB.toString()));
        assertFalse(ab.toString().contains(fitnessA.toString()));
        assertTrue(ab.toString().contains(fitnessB.toString()));
        assertFalse(ba.toString().contains(bitStringA.toString()));
        assertTrue(ba.toString().contains(bitStringB.toString()));
        assertTrue(ba.toString().contains(fitnessA.toString()));
        assertFalse(ba.toString().contains(fitnessB.toString()));
        assertFalse(bb.toString().contains(bitStringA.toString()));
        assertTrue(bb.toString().contains(bitStringB.toString()));
        assertFalse(bb.toString().contains(fitnessA.toString()));
        assertTrue(bb.toString().contains(fitnessB.toString()));
    }

}