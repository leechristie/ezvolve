package org.ezvolve.core.evaluation;

import java.util.*;
import org.ezvolve.core.encoding.BitString;
import org.junit.Assert;
import org.junit.Test;

public class FitnessComparatorTest {

    @Test
    public void testMinFitness() {
        Fitness[] actuals = new Fitness[] {
                Fitness.of(1.0),
                null,
                Fitness.of(100.0),
                null,
                Fitness.of(42.0),
                null};
        Fitness[] expected = new Fitness[] {
                null,
                null,
                null,
                Fitness.of(100.0),
                Fitness.of(42.0),
                Fitness.of(1.0)};
        Arrays.sort(actuals, FitnessComparator.MINIMIZATION);
        Assert.assertArrayEquals(expected, actuals);
    }

    @Test
    public void testMaxFitness() {
        Fitness[] actuals = new Fitness[] {
                Fitness.of(1.0),
                null,
                Fitness.of(100.0),
                null,
                Fitness.of(42.0),
                null};
        Fitness[] expected = new Fitness[] {
                null,
                null,
                null,
                Fitness.of(1.0),
                Fitness.of(42.0),
                Fitness.of(100.0)};
        Arrays.sort(actuals, FitnessComparator.MAXIMIZATION);
        Assert.assertArrayEquals(expected, actuals);
    }

    @Test
    public void testMinEvaluated() {
        
        List<Evaluated<BitString>> actuals = new ArrayList<>(4);
        actuals.add(Evaluated.of(BitString.of(0, 0, 1), Fitness.of(1)));
        actuals.add(null);
        actuals.add(Evaluated.of(BitString.of(1, 0, 1), Fitness.of(5)));
        actuals.add(Evaluated.of(BitString.of(0, 1, 1), Fitness.of(3)));
        
        List<Evaluated<BitString>> expected = new ArrayList<>(4);
        expected.add(null);
        expected.add(Evaluated.of(BitString.of(1, 0, 1), Fitness.of(5)));
        expected.add(Evaluated.of(BitString.of(0, 1, 1), Fitness.of(3)));
        expected.add(Evaluated.of(BitString.of(0, 0, 1), Fitness.of(1)));
        
        Comparator<Evaluated<BitString>> comp =
                FitnessComparator.MINIMIZATION.evaluatedComparator();
        Collections.sort(actuals, comp);
        Assert.assertEquals(expected.get(0), actuals.get(0));
        Assert.assertEquals(expected.get(1), actuals.get(1));
        Assert.assertEquals(expected.get(2), actuals.get(2));
        Assert.assertEquals(expected.get(3), actuals.get(3));
        
    }

    @Test
    public void testMaxEvaluated() {
        
        List<Evaluated<BitString>> actuals = new ArrayList<>(4);
        actuals.add(Evaluated.of(BitString.of(0, 0, 1), Fitness.of(1)));
        actuals.add(null);
        actuals.add(Evaluated.of(BitString.of(1, 0, 1), Fitness.of(5)));
        actuals.add(Evaluated.of(BitString.of(0, 1, 1), Fitness.of(3)));
        
        List<Evaluated<BitString>> expected = new ArrayList<>(4);
        expected.add(null);
        expected.add(Evaluated.of(BitString.of(0, 0, 1), Fitness.of(1)));
        expected.add(Evaluated.of(BitString.of(0, 1, 1), Fitness.of(3)));
        expected.add(Evaluated.of(BitString.of(1, 0, 1), Fitness.of(5)));
        
        Comparator<Evaluated<BitString>> comp =
                FitnessComparator.MAXIMIZATION.evaluatedComparator();
        Collections.sort(actuals, comp);
        Assert.assertEquals(expected.get(0), actuals.get(0));
        Assert.assertEquals(expected.get(1), actuals.get(1));
        Assert.assertEquals(expected.get(2), actuals.get(2));
        Assert.assertEquals(expected.get(3), actuals.get(3));
        
    }
    
    @Test
    public void testMinBetter() {
        Fitness worse = Fitness.of(100.0);
        Fitness better = Fitness.of(1.0);
        Assert.assertSame(better,
                FitnessComparator.MINIMIZATION.bestOf(worse, better));
        Assert.assertSame(better,
                FitnessComparator.MINIMIZATION.bestOf(better, worse));
        Assert.assertSame(better,
                FitnessComparator.MINIMIZATION.bestOf(null, better));
        Assert.assertSame(better,
                FitnessComparator.MINIMIZATION.bestOf(better, null));
        Assert.assertSame(worse,
                FitnessComparator.MINIMIZATION.bestOf(null, worse));
        Assert.assertSame(worse, 
                FitnessComparator.MINIMIZATION.bestOf(worse, null));
    }
    
    @Test
    public void testMaxBetter() {
        Fitness worse = Fitness.of(100.0);
        Fitness better = Fitness.of(1.0);
        Assert.assertSame(better,
                FitnessComparator.MINIMIZATION.bestOf(worse, better));
        Assert.assertSame(better,
                FitnessComparator.MINIMIZATION.bestOf(better, worse));
        Assert.assertSame(better, 
                FitnessComparator.MINIMIZATION.bestOf(null, better));
        Assert.assertSame(better,
                FitnessComparator.MINIMIZATION.bestOf(better, null));
        Assert.assertSame(worse,
                FitnessComparator.MINIMIZATION.bestOf(null, worse));
        Assert.assertSame(worse,
                FitnessComparator.MINIMIZATION.bestOf(worse, null));
    }

}
