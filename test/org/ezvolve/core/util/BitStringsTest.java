package org.ezvolve.core.util;

import java.util.Random;
import org.ezvolve.core.encoding.BitString;
import org.ezvolve.core.stochastic.CandidateSource;
import org.ezvolve.core.stochastic.Probability;
import org.ezvolve.core.stochastic.ProbabilityVector;
import org.junit.*;
import static org.junit.Assert.*;

public class BitStringsTest {
    
    @Test(expected=IllegalArgumentException.class)
    public void testUniformDistributionNegative() {
        BitStrings.uniformDistribution(-1);
    }

    @Test
    public void testUniformDistribution() {
        
        final int NUMBER_OF_VECTORS_TO_TEST = 10;
        final int BITSTRINGS_PER_VECTOR = 100000;
        
        Random lengthGenerator = new Random(100);
        Random random = new Random(1337);
        
        for (int i = 0; i < NUMBER_OF_VECTORS_TO_TEST; i++) {
            
            // Creates a random-length candidate source
            int length = lengthGenerator.nextInt(10) + 5;
            CandidateSource<BitString> source
                    = BitStrings.uniformDistribution(length);
            
            // Generates bit strings to count bits
            int[] counts = new int[length];
            for (int j = 0; j < BITSTRINGS_PER_VECTOR; j++) {
                BitString bitString = source.sample(random);
                for (int index = 0; index < bitString.length(); index++) {
                    counts[index] += bitString.bit(index);
                }
            }
            
            // Checks the expected value
            for (int index = 0; index < length; index++) {
                double actual = counts[index] / (double) BITSTRINGS_PER_VECTOR;
                assertEquals(0.5, actual, 0.01);
            }
            
        }
        
        
    }
    
}
