package org.ezvolve.core.encoding;

import com.gargoylesoftware.base.testing.EqualsTester;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.ezvolve.core.test.ExceptionTestUtils.*;

public final class BitStringTest {
    
    @Test
    public void testFactoryBoolean() {
        assertEquals("", BitString.of().toString());
        assertEquals("0", BitString.of(false).toString());
        assertEquals("1", BitString.of(true).toString());
        assertEquals("00", BitString.of(false, false).toString());
        assertEquals("01", BitString.of(false, true).toString());
        assertEquals("10", BitString.of(true, false).toString());
        assertEquals("11", BitString.of(true, true).toString());
        assertEquals("000", BitString.of(false, false, false).toString());
        assertEquals("001", BitString.of(false, false, true).toString());
        assertEquals("010", BitString.of(false, true, false).toString());
        assertEquals("011", BitString.of(false, true, true).toString());
        assertEquals("100", BitString.of(true, false, false).toString());
        assertEquals("101", BitString.of(true, false, true).toString());
        assertEquals("110", BitString.of(true, true, false).toString());
        assertEquals("111", BitString.of(true, true, true).toString());
        boolean[] longArray = new boolean[10];
        assertEquals("0000000000", BitString.of(longArray).toString());
        longArray[1] = true;
        assertEquals("0100000000", BitString.of(longArray).toString());
    }

    @Test
    public void testFactoryInteger() {
        assertEquals("", BitString.of().toString());
        assertEquals("0", BitString.of(0).toString());
        assertEquals("1", BitString.of(1).toString());
        assertEquals("00", BitString.of(0, 0).toString());
        assertEquals("01", BitString.of(0, 1).toString());
        assertEquals("10", BitString.of(1, 0).toString());
        assertEquals("11", BitString.of(1, 1).toString());
        assertEquals("000", BitString.of(0, 0, 0).toString());
        assertEquals("001", BitString.of(0, 0, 1).toString());
        assertEquals("010", BitString.of(0, 1, 0).toString());
        assertEquals("011", BitString.of(0, 1, 1).toString());
        assertEquals("100", BitString.of(1, 0, 0).toString());
        assertEquals("101", BitString.of(1, 0, 1).toString());
        assertEquals("110", BitString.of(1, 1, 0).toString());
        assertEquals("111", BitString.of(1, 1, 1).toString());
        int[] longArray = new int[10];
        assertEquals("0000000000", BitString.of(longArray).toString());
        longArray[1] = 1;
        assertEquals("0100000000", BitString.of(longArray).toString());
    }

    @Test
    public void testLength() {
        for (int i = 0; i < 10; i++) {
            assertEquals(i, BitString.of(new int[i]).length());
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBinaryDigitTwo() {
        BitString.of(2);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBinaryDigitNegativeOne() {
        BitString.of(-1);
    }

    @Test
    public void testNiceFactoryDigitErrors() {
        final int LENGTH = 10;
        for (int i = 0; i < LENGTH; i++) {
            niceFactoryHelper(LENGTH, -1, i);
            niceFactoryHelper(LENGTH, 2, i);
        }
        niceFactoryHelper(LENGTH, 42, 0);
        niceFactoryHelper(LENGTH, Integer.MAX_VALUE, 0);
        niceFactoryHelper(LENGTH, Integer.MIN_VALUE, 0);
    }

    private void niceFactoryHelper(int length, int illegalDigit, int index) {
        int[] digits = new int[length];
        digits[index] = illegalDigit;
        try {
            BitString.of(digits);
            fail();
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertExceptionMessageContains(ex, illegalDigit, "at index", index);
        }
    }

    @Test(expected=NullPointerException.class)
    public void testNullIntegerFactory() {
        BitString.of((int[]) null);
    }

    @Test(expected=NullPointerException.class)
    public void testNullBooleanFactory() {
        BitString.of((boolean[]) null);
    }
    
    @Test
    public void testEquals() {
        BitString a = BitString.of(0, 1, 0, 0); // original
        BitString b = BitString.of(0, 1, 0, 0); // same
        BitString c = BitString.of(1, 0, 0);    // different
        BitString d = null;                     // subclass
        new EqualsTester(a, b, c, d);
    }

    @Test
    public void testEqualsWithDifferentFactories() {
        BitString a = BitString.of(0, 1, 0, 0);                // original
        BitString b = BitString.of(false, true, false, false); // same
        BitString c = BitString.of(true, false, false);        // different
        BitString d = null;                                    // subclass
        new EqualsTester(a, b, c, d);
    }

    @Test
    public void testEncapsulationFromArrayInteger() {
        int[] theArray = new int[] {0, 1, 0, 1, 0, 1};
        BitString a = BitString.of(theArray);                     // original
        BitString b = BitString.of(new int[] {0, 1, 0, 1, 0, 1}); // same
        theArray[2] = 1;
        BitString c = BitString.of(theArray);                     // different
        BitString d = null;                                       // subclass
        new EqualsTester(a, b, c, d);
    }

    @Test
    public void testEncapsulationFromArrayBoolean() {
        boolean[] theArray = new boolean[] {false, false};
        BitString a = BitString.of(theArray);                     // original
        BitString b = BitString.of(new boolean[] {false, false}); // same
        theArray[1] = true;
        BitString c = BitString.of(theArray);                     // different
        BitString d = null;                                       // subclass
        new EqualsTester(a, b, c, d);
    }

    @Test
    public void testWithBit() {
        BitString tenZeros = BitString.of(new boolean[10]);
        BitString another1 = tenZeros.withBit(4, true);
        BitString another2 = another1.withBit(0, true);
        BitString another3 = another2.withBit(4, false);
        assertEquals("0000000000", tenZeros.toString());
        assertEquals("0000100000", another1.toString());
        assertEquals("1000100000", another2.toString());
        assertEquals("1000000000", another3.toString());
    }

    @Test
    public void testWithBitError() {
        BitString bitString = BitString.of(1, 1, 0, 1, 0);
        try {
            bitString.withBit(-1, true);
            fail();
        } catch (Exception ex) {
            assertEquals(IndexOutOfBoundsException.class, ex.getClass());
            assertExceptionMessageContains(ex, "index", "-1", "0 to 4");
        }
        try {
            bitString.withBit(5, true);
            fail();
        } catch (Exception ex) {
            assertEquals(IndexOutOfBoundsException.class, ex.getClass());
            assertExceptionMessageContains(ex, "index", "5", "0 to 4");
        }
    }

    @Test
    public void testWithBitOptimisation() {
        BitString zeros = BitString.of(0, 0, 0);
        BitString sameZeros = zeros.withBit(0, false);
        assertSame(zeros, sameZeros);
        BitString ones = BitString.of(1, 1, 1);
        BitString sameOnes = ones.withBit(0, true);
        assertSame(ones, sameOnes);
    }

    @Test
    public void testIsSet() {
        BitString a = BitString.of(0, 1, 0, 0);
        assertFalse(a.isSet(0));
        assertTrue(a.isSet(1));
        assertFalse(a.isSet(2));
        assertFalse(a.isSet(3));
        BitString b = BitString.of(1, 1, 0, 1, 0);
        assertTrue(b.isSet(0));
        assertTrue(b.isSet(1));
        assertFalse(b.isSet(2));
        assertTrue(b.isSet(3));
        assertFalse(b.isSet(4));
    }

    @Test
    public void testIsSetError() {
        BitString bitString = BitString.of(1, 1, 0, 1, 0);
        try {
            bitString.isSet(-1);
            fail();
        } catch (Exception ex) {
            assertEquals(IndexOutOfBoundsException.class, ex.getClass());
            assertExceptionMessageContains(ex, "index", "-1", "0 to 4");
        }
        try {
            bitString.isSet(5);
            fail();
        } catch (Exception ex) {
            assertEquals(IndexOutOfBoundsException.class, ex.getClass());
            assertExceptionMessageContains(ex, "index", "5", "0 to 4");
        }
    }

    @Test
    public void testGetBit() {
        BitString a = BitString.of(0, 1, 0, 0);
        assertEquals(0, a.bit(0));
        assertEquals(1, a.bit(1));
        assertEquals(0, a.bit(2));
        assertEquals(0, a.bit(3));
        BitString b = BitString.of(1, 1, 0, 1, 0);
        assertEquals(1, b.bit(0));
        assertEquals(1, b.bit(1));
        assertEquals(0, b.bit(2));
        assertEquals(1, b.bit(3));
        assertEquals(0, b.bit(4));
    }

    @Test
    public void testGetBitError() {
        BitString bitString = BitString.of(1, 1, 0, 1, 0);
        try {
            bitString.bit(-1);
            fail();
        } catch (Exception ex) {
            assertEquals(IndexOutOfBoundsException.class, ex.getClass());
            assertExceptionMessageContains(ex, "index", "-1", "0 to 4");
        }
        try {
            bitString.bit(5);
            fail();
        } catch (Exception ex) {
            assertEquals(IndexOutOfBoundsException.class, ex.getClass());
            assertExceptionMessageContains(ex, "index", "5", "0 to 4");
        }
    }

    @Test
    public void testHammingDistance() {
        BitString origonal = BitString.of(0, 1, 1);
        BitString b000 = BitString.of(0, 0, 0); // 2
        BitString b001 = BitString.of(0, 0, 1); // 1
        BitString b010 = BitString.of(0, 1, 0); // 1
        BitString b011 = BitString.of(0, 1, 1); // 0
        BitString b100 = BitString.of(1, 0, 0); // 3
        BitString b101 = BitString.of(1, 0, 1); // 2
        BitString b110 = BitString.of(1, 1, 0); // 2
        BitString b111 = BitString.of(1, 1, 1); // 1
        assertEquals(2, origonal.hammingDistanceTo(b000));
        assertEquals(2, b000.hammingDistanceTo(origonal));
        assertEquals(1, origonal.hammingDistanceTo(b001));
        assertEquals(1, b001.hammingDistanceTo(origonal));
        assertEquals(1, origonal.hammingDistanceTo(b010));
        assertEquals(1, b010.hammingDistanceTo(origonal));
        assertEquals(0, origonal.hammingDistanceTo(b011));
        assertEquals(0, b011.hammingDistanceTo(origonal));
        assertEquals(3, origonal.hammingDistanceTo(b100));
        assertEquals(3, b100.hammingDistanceTo(origonal));
        assertEquals(2, origonal.hammingDistanceTo(b101));
        assertEquals(2, b101.hammingDistanceTo(origonal));
        assertEquals(2, origonal.hammingDistanceTo(b110));
        assertEquals(2, b110.hammingDistanceTo(origonal));
        assertEquals(1, origonal.hammingDistanceTo(b111));
        assertEquals(1, b111.hammingDistanceTo(origonal));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testHammingDistanceWrongLength() {
        BitString len4 = BitString.of(new boolean[4]);
        BitString len10 = BitString.of(new boolean[10]);
        len4.hammingDistanceTo(len10);
    }

    @Test
    public void testHammingDistanceWrongLengthNiceMessage() {
        try {
            BitString.of(new boolean[4])
                    .hammingDistanceTo(BitString.of(new boolean[10]));
            fail();
        } catch (IllegalArgumentException ex) {
            assertExceptionMessageContains(ex, 4, 10);
        }
        try {
            BitString.of(new boolean[3])
                    .hammingDistanceTo(BitString.of(new boolean[5]));
            fail();
        } catch (IllegalArgumentException ex) {
            assertExceptionMessageContains(ex, 3, 5);
        }
    }
    
}