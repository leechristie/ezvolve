package org.ezvolve.core.evaluation.benchmark;

import org.ezvolve.core.encoding.BitString;
import org.ezvolve.core.evaluation.Fitness;
import org.ezvolve.core.evaluation.FitnessFunction;
import org.ezvolve.core.util.BitOrder;
import org.junit.*;
import static org.junit.Assert.*;

public class BinValTest {

    @Test(expected=NullPointerException.class)
    public void testGetInstanceNull() {
        FitnessFunction<BitString> instance = BinVal.getInstance(null);
        assertNotNull(instance);
    }

    @Test
    public void testGetInstance() {
        FitnessFunction<BitString> instance
                = BinVal.getInstance(BitOrder.LOW_INDEX_LEAST_SIGNIFICANT);
        assertNotNull(instance);
        instance = BinVal.getInstance(BitOrder.LOW_INDEX_MOST_SIGNIFICANT);
        assertNotNull(instance);
    }
    
    @Test(expected=NullPointerException.class)
    public void testEvaluateNullMost() {
        FitnessFunction<BitString> instance
                = BinVal.getInstance(BitOrder.LOW_INDEX_MOST_SIGNIFICANT);
        BitString simple = BitString.of();
        Fitness fitness = instance.evaluate(null);
    }
    
    @Test(expected=NullPointerException.class)
    public void testEvaluateNullLeast() {
        FitnessFunction<BitString> instance
                = BinVal.getInstance(BitOrder.LOW_INDEX_LEAST_SIGNIFICANT);
        BitString simple = BitString.of();
        Fitness fitness = instance.evaluate(null);
    }
    
    @Test
    public void testEvaluateMost() {
        FitnessFunction<BitString> instance
                = BinVal.getInstance(BitOrder.LOW_INDEX_MOST_SIGNIFICANT);
        assertEquals(Fitness.of(0), instance.evaluate(BitString.of()));
        assertEquals(Fitness.of(0), instance.evaluate(BitString.of(0)));
        assertEquals(Fitness.of(1), instance.evaluate(BitString.of(1)));
        assertEquals(Fitness.of(0), instance.evaluate(BitString.of(0, 0)));
        assertEquals(Fitness.of(1), instance.evaluate(BitString.of(0, 1)));
        assertEquals(Fitness.of(2), instance.evaluate(BitString.of(1, 0)));
        assertEquals(Fitness.of(3), instance.evaluate(BitString.of(1, 1)));
        assertEquals(Fitness.of(0), instance.evaluate(BitString.of(0, 0, 0)));
        assertEquals(Fitness.of(1), instance.evaluate(BitString.of(0, 0, 1)));
        assertEquals(Fitness.of(2), instance.evaluate(BitString.of(0, 1, 0)));
        assertEquals(Fitness.of(3), instance.evaluate(BitString.of(0, 1, 1)));
        assertEquals(Fitness.of(4), instance.evaluate(BitString.of(1, 0, 0)));
        assertEquals(Fitness.of(5), instance.evaluate(BitString.of(1, 0, 1)));
        assertEquals(Fitness.of(6), instance.evaluate(BitString.of(1, 1, 0)));
        assertEquals(Fitness.of(7), instance.evaluate(BitString.of(1, 1, 1)));
    }
    
    @Test
    public void testEvaluateLeast() {
        FitnessFunction<BitString> instance
                = BinVal.getInstance(BitOrder.LOW_INDEX_LEAST_SIGNIFICANT);
        assertEquals(Fitness.of(0), instance.evaluate(BitString.of()));
        assertEquals(Fitness.of(0), instance.evaluate(BitString.of(0)));
        assertEquals(Fitness.of(1), instance.evaluate(BitString.of(1)));
        assertEquals(Fitness.of(0), instance.evaluate(BitString.of(0, 0)));
        assertEquals(Fitness.of(2), instance.evaluate(BitString.of(0, 1)));
        assertEquals(Fitness.of(1), instance.evaluate(BitString.of(1, 0)));
        assertEquals(Fitness.of(3), instance.evaluate(BitString.of(1, 1)));
        assertEquals(Fitness.of(0), instance.evaluate(BitString.of(0, 0, 0)));
        assertEquals(Fitness.of(4), instance.evaluate(BitString.of(0, 0, 1)));
        assertEquals(Fitness.of(2), instance.evaluate(BitString.of(0, 1, 0)));
        assertEquals(Fitness.of(6), instance.evaluate(BitString.of(0, 1, 1)));
        assertEquals(Fitness.of(1), instance.evaluate(BitString.of(1, 0, 0)));
        assertEquals(Fitness.of(5), instance.evaluate(BitString.of(1, 0, 1)));
        assertEquals(Fitness.of(3), instance.evaluate(BitString.of(1, 1, 0)));
        assertEquals(Fitness.of(7), instance.evaluate(BitString.of(1, 1, 1)));
    }
    
    @Test
    public void testEvaluateMostExtreme() {
        FitnessFunction<BitString> instance
                = BinVal.getInstance(BitOrder.LOW_INDEX_MOST_SIGNIFICANT);
        assertEquals(Fitness.of(0b0000000000000000000000000000000),
                instance.evaluate(BitString.of(
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)));
        assertEquals(Fitness.of(0b0000000000000000000000000000001),
                instance.evaluate(BitString.of(
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1)));
        assertEquals(Fitness.of(0b1000000000000000000000000000000),
                instance.evaluate(BitString.of(
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)));
        assertEquals(Fitness.of(0b1111111111111111111111111111111),
                instance.evaluate(BitString.of(
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)));
    }
    
    @Test
    public void testEvaluateLeastExtreme() {
        FitnessFunction<BitString> instance
                = BinVal.getInstance(BitOrder.LOW_INDEX_LEAST_SIGNIFICANT);
        assertEquals(Fitness.of(0b0000000000000000000000000000000),
                instance.evaluate(BitString.of(
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)));
        assertEquals(Fitness.of(0b0000000000000000000000000000001),
                instance.evaluate(BitString.of(
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)));
        assertEquals(Fitness.of(0b1000000000000000000000000000000),
                instance.evaluate(BitString.of(
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1)));
        assertEquals(Fitness.of(0b1111111111111111111111111111111),
                instance.evaluate(BitString.of(
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testEvaluateMostExtremeException() {
        FitnessFunction<BitString> instance
                = BinVal.getInstance(BitOrder.LOW_INDEX_MOST_SIGNIFICANT);
        instance.evaluate(BitString.of(new boolean[32]));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testEvaluateLeastExtremeException() {
        FitnessFunction<BitString> instance
                = BinVal.getInstance(BitOrder.LOW_INDEX_LEAST_SIGNIFICANT);
        instance.evaluate(BitString.of(new boolean[32]));
    }

}
