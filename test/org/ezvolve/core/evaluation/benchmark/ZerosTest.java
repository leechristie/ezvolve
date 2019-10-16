package org.ezvolve.core.evaluation.benchmark;

import org.ezvolve.core.encoding.BitString;
import org.ezvolve.core.evaluation.Fitness;
import org.ezvolve.core.evaluation.FitnessFunction;
import org.junit.*;
import static org.junit.Assert.*;

public class ZerosTest {

    @Test
    public void testGetInstance() {
        FitnessFunction<BitString> instance = Zeros.getInstance();
        assertNotNull(instance);
    }
    
    @Test
    public void testEvaluate() {
        FitnessFunction<BitString> instance = Zeros.getInstance();
        BitString simple = BitString.of();
        Fitness fitness = instance.evaluate(simple);
        assertNotNull(fitness);
    }
    
    @Test(expected=NullPointerException.class)
    public void testEvaluateNull() {
        FitnessFunction<BitString> instance = Zeros.getInstance();
        BitString simple = BitString.of();
        Fitness fitness = instance.evaluate(null);
    }
    
    @Test
    public void testEvaluateDetailed() {
        FitnessFunction<BitString> instance = Zeros.getInstance();
        BitString simple = BitString.of();
        assertEquals(Fitness.of(0), instance.evaluate(BitString.of()));
        assertEquals(Fitness.of(1), instance.evaluate(BitString.of(0)));
        assertEquals(Fitness.of(0), instance.evaluate(BitString.of(1)));
        assertEquals(Fitness.of(2), instance.evaluate(BitString.of(0, 0)));
        assertEquals(Fitness.of(1), instance.evaluate(BitString.of(0, 1)));
        assertEquals(Fitness.of(1), instance.evaluate(BitString.of(1, 0)));
        assertEquals(Fitness.of(0), instance.evaluate(BitString.of(1, 1)));
        assertEquals(Fitness.of(3), instance.evaluate(BitString.of(0, 0, 0)));
        assertEquals(Fitness.of(2), instance.evaluate(BitString.of(0, 0, 1)));
        assertEquals(Fitness.of(2), instance.evaluate(BitString.of(0, 1, 0)));
        assertEquals(Fitness.of(1), instance.evaluate(BitString.of(0, 1, 1)));
        assertEquals(Fitness.of(2), instance.evaluate(BitString.of(1, 0, 0)));
        assertEquals(Fitness.of(1), instance.evaluate(BitString.of(1, 0, 1)));
        assertEquals(Fitness.of(1), instance.evaluate(BitString.of(1, 1, 0)));
        assertEquals(Fitness.of(0), instance.evaluate(BitString.of(1, 1, 1)));
    }
    
}
