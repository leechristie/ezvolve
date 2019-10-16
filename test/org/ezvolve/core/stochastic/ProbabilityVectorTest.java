package org.ezvolve.core.stochastic;

import com.gargoylesoftware.base.testing.EqualsTester;
import java.util.Random;
import org.ezvolve.core.encoding.BitString;
import org.junit.*;
import static org.junit.Assert.*;
import static org.ezvolve.core.test.ExceptionTestUtils.*;

public class ProbabilityVectorTest {

    @Test
    public void testEmptyFactory() {
        assertEquals("P[]", ProbabilityVector.of().toString());
    }

    @Test
    public void testProbabilityFactory() {
        assertEquals("P[1.0]", ProbabilityVector.of(
                Probability.of(1.0)).toString());
        assertEquals("P[0.0]", ProbabilityVector.of(
                Probability.of(0.0)).toString());
        assertEquals("P[0.5, 1.0]", ProbabilityVector.of(
                Probability.of(0.5), Probability.of(1.0)).toString());
    }

    @Test
    public void testDoubleFactory() {
        assertEquals("P[1.0]", ProbabilityVector.of(1.0).toString());
        assertEquals("P[0.0]", ProbabilityVector.of(0.0).toString());
        assertEquals("P[0.5, 1.0]", ProbabilityVector.of(0.5, 1.0).toString());
    }

    @Test(expected=NullPointerException.class)
    public void testProbabilityFactoryNull() {
        ProbabilityVector.of((Probability[]) null);
    }

    @Test(expected=NullPointerException.class)
    public void testDoubleFactoryNull() {
        ProbabilityVector.of((double[]) null);
    }

    @Test
    public void testProbabilityFactoryNullElements() {
        try {
            ProbabilityVector.of(new Probability[] {null});
            fail();
        } catch (NullPointerException ex) {
            assertExceptionMessageContains(ex, "probabilities", 0);
        }
        try {
            ProbabilityVector.of(new Probability[] {null, Probability.of(0.0)});
            fail();
        } catch (NullPointerException ex) {
            assertExceptionMessageContains(ex, "probabilities", 0);
        }
        try {
            ProbabilityVector.of(new Probability[] {Probability.of(1.0), null});
            fail();
        } catch (NullPointerException ex) {
            assertExceptionMessageContains(ex, "probabilities", 1);
        }
    }

    @Test
    public void testNiceFactoryDigitErrors() {
        final int LENGTH = 10;
        for (int i = 0; i < LENGTH; i++) {
            niceFactoryHelper(LENGTH, 
                    Math.nextAfter(1.0, Double.POSITIVE_INFINITY), i);
            niceFactoryHelper(LENGTH, 
                    Math.nextAfter(0.0, Double.NEGATIVE_INFINITY), i);
            niceFactoryHelper(LENGTH,
                    Double.NaN, i);
        }
    }

    private void niceFactoryHelper(int length, double illegalValue, int index) {
        double[] values = new double[length];
        values[index] = illegalValue;
        try {
            ProbabilityVector.of(values);
            fail();
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertExceptionMessageContains(ex, illegalValue, "at index", index);
        }
    }
    
    @Test
    public void testProbability() {
        assertEquals(Probability.of(0.5), 
                ProbabilityVector.of(0.5).probability(0));
        assertEquals(Probability.of(0.1),
                ProbabilityVector.of(0.1).probability(0));
        assertEquals(Probability.of(0.2), 
                ProbabilityVector.of(0.2, 0.7).probability(0));
        assertEquals(Probability.of(0.3), 
                ProbabilityVector.of(0.4, 0.3).probability(1));
    }
    
    @Test
    public void testDoubleValue() {
        assertEquals(0.5, ProbabilityVector.of(0.5).doubleValue(0), 0.0);
        assertEquals(0.1, ProbabilityVector.of(0.1).doubleValue(0), 0.0);
        assertEquals(0.2, ProbabilityVector.of(0.2, 0.7).doubleValue(0), 0.0);
        assertEquals(0.3, ProbabilityVector.of(0.4, 0.3).doubleValue(1), 0.0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDoubleFactoryLowerRangeError() {
        ProbabilityVector.of(Math.nextAfter(0.0, Double.NEGATIVE_INFINITY));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDoubleFactoryUpperRangeError() {
        ProbabilityVector.of(Math.nextAfter(0.0, Double.NEGATIVE_INFINITY));
    }

    @Test
    public void testEquals() {
        Probability[] theArray = new Probability[] {Probability.of(0.1),
                                                    Probability.of(0.2)};
        ProbabilityVector a = ProbabilityVector.of(0.1, 0.2);
        ProbabilityVector b = ProbabilityVector.of(
                new Probability[] {Probability.of(0.1),
                                   Probability.of(0.2)});
        ProbabilityVector c = ProbabilityVector.of(
                new Probability[] {Probability.of(0.1),
                                   Probability.of(0.3)});
        new EqualsTester(a, b, c, null);
    }

    @Test
    public void testEncapsulationFromArrayProb() {
        Probability[] theArray = new Probability[] {Probability.of(0.1),
                                                    Probability.of(0.2)};
        ProbabilityVector a = ProbabilityVector.of(theArray);
        ProbabilityVector b = ProbabilityVector.of(
                new Probability[] {Probability.of(0.1),
                                   Probability.of(0.2)});
        theArray[1] = Probability.of(0.3);
        ProbabilityVector c = ProbabilityVector.of(theArray);
        new EqualsTester(a, b, c, null);
    }

    @Test
    public void testEncapsulationFromArrayDouble() {
        double[] theArray = new double[] {0.1, 0.2};
        ProbabilityVector a = ProbabilityVector.of(theArray);
        ProbabilityVector b = ProbabilityVector.of(new double[] {0.1, 0.2});
        theArray[1] = 0.3;
        ProbabilityVector c = ProbabilityVector.of(theArray);
        new EqualsTester(a, b, c, null);
    }

    @Test
    public void testNextBooleanNull() {
        try {
            ProbabilityVector.of(0.5).nextBoolean(0, null);
            fail();
        } catch (NullPointerException expected) {
            assertExceptionMessageContains(expected, "random");
        }
    }

    @Test
    public void testNextBooleanRange() {
        try {
            ProbabilityVector.of(0.5).nextBoolean(-1, new Random());
            fail();
        } catch (IndexOutOfBoundsException expected) {
            assertExceptionMessageContains(expected, "index", "-1", "0 to 0");
        }
        try {
            ProbabilityVector.of(0.5).nextBoolean(1, new Random());
            fail();
        } catch (IndexOutOfBoundsException expected) {
            assertExceptionMessageContains(expected, "index", "1", "0 to 0");
        }
    }

    @Test
    public void testSample() {
        assertEquals(BitString.of(),
                ProbabilityVector.of().sample(new Random()));
        assertEquals(1,
                ProbabilityVector.of(0.5).sample(new Random()).length());
        assertEquals(2,
                ProbabilityVector.of(0.5, 0.5).sample(new Random()).length());
    }

    @Test
    public void testSampleNull() {
        try {
            ProbabilityVector.of().sample(null);
            fail();
        } catch (NullPointerException expected) {
            assertExceptionMessageContains(expected, "random");
        }
    }

    @Test
    public void testNextBooleanStochastic() {
        
        final int NUMBER_OF_VECTORS_TO_TEST = 10;
        final int TESTS_PER_PROBABILIY = 100;
        
        Random lengthGenerator = new Random(100);
        Random probGenerator = new Random(42);
        Random seedGenerator = new Random(1337);
        
        for (int i = 0; i < NUMBER_OF_VECTORS_TO_TEST; i++) {
            
            // Creates a random probability vector
            int length = lengthGenerator.nextInt(10) + 5;
            double[] probabilities = new double[length];
            for (int index = 0; index < length; index++) {
                probabilities[index] = probGenerator.nextDouble();
            }
            ProbabilityVector vector = ProbabilityVector.of(probabilities);
            
            // Checks that each element operates as expected
            for (int index = 0; index < length; index++) {
                for (int j = 0; j < TESTS_PER_PROBABILIY; j++) {
                    long seed = seedGenerator.nextLong();
                    Random r1 = new Random(seed);
                    Random r2 = new Random(seed);
                    assertEquals(vector.probability(index).nextBoolean(r1),
                                 vector.nextBoolean(index, r2));
                }
            }
            
        }
        
    }

    @Test
    public void testSampleExact() {
        Random r = new Random();
        ProbabilityVector probOneZero
                = ProbabilityVector.of(1.0, 0.0, 1.0, 0.0, 1.0, 0.0);
        BitString oneZero = BitString.of(1, 0, 1, 0, 1, 0);
        for (int i = 0; i < 10000; i++) {
            assertEquals(oneZero, probOneZero.sample(r));
        }
        ProbabilityVector probZeroOne
                = ProbabilityVector.of(0.0, 1.0, 0.0, 1.0, 0.0, 1.0);
        BitString zeroOne = BitString.of(0, 1, 0, 1, 0, 1);
        for (int i = 0; i < 10000; i++) {
            assertEquals(zeroOne, probZeroOne.sample(r));
        }
    }

    @Test
    public void testSampleStochastic() {
        
        final int NUMBER_OF_VECTORS_TO_TEST = 10;
        final int BITSTRINGS_PER_VECTOR = 100000;
        
        Random lengthGenerator = new Random(100);
        Random probGenerator = new Random(42);
        Random random = new Random(1337);
        
        for (int i = 0; i < NUMBER_OF_VECTORS_TO_TEST; i++) {
            
            // Creates a random probability vector
            int length = lengthGenerator.nextInt(10) + 5;
            double[] probabilities = new double[length];
            for (int index = 0; index < length; index++) {
                probabilities[index] = probGenerator.nextDouble();
            }
            ProbabilityVector vector = ProbabilityVector.of(probabilities);
            
            // Generates bit strings to count bits
            int[] counts = new int[length];
            for (int j = 0; j < BITSTRINGS_PER_VECTOR; j++) {
                BitString bitString = vector.sample(random);
                for (int index = 0; index < bitString.length(); index++) {
                    counts[index] += bitString.bit(index);
                }
            }
            
            // Checks the expected value
            for (int index = 0; index < length; index++) {
                double expected = vector.doubleValue(index);
                double actual = counts[index] / (double) BITSTRINGS_PER_VECTOR;
                assertEquals(expected, actual, 0.01);
            }
            
        }
        
        
    }
    
}
