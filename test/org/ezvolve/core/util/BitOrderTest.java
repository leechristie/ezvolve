package org.ezvolve.core.util;

import org.junit.*;
import static org.junit.Assert.*;
import static org.ezvolve.core.test.ExceptionTestUtils.*;

public class BitOrderTest {

    @Test
    public void testLILS() {
        BitOrder rightMsb = BitOrder.LOW_INDEX_LEAST_SIGNIFICANT;
        assertEquals("rightMsb.bitValue(0, 4)", 1, rightMsb.bitValue(0, 4));
        assertEquals("rightMsb.bitValue(1, 4)", 2, rightMsb.bitValue(1, 4));
        assertEquals("rightMsb.bitValue(2, 4)", 4, rightMsb.bitValue(2, 4));
        assertEquals("rightMsb.bitValue(3, 4)", 8, rightMsb.bitValue(3, 4));
        assertEquals("rightMsb.bitValue(0, 31)",
                0b0000000000000000000000000000001, rightMsb.bitValue(0, 31));
        assertEquals("rightMsb.bitValue(30, 31)",
                0b1000000000000000000000000000000, rightMsb.bitValue(30, 31));
        int value;
        value = 1;
        for (int i = 0; i < 8; i++) {
            assertEquals("rightMsb.bitValue(" + i + ", " + 8 + ")",
                    value, rightMsb.bitValue(i, 8));
            value *= 2;
        }
        value = 1;
        for (int i = 0; i < 5; i++) {
            assertEquals("rightMsb.bitValue(" + i + ", " + 5 + ")",
                    value, rightMsb.bitValue(i, 5));
            value *= 2;
        }
        value = 1;
        for (int i = 0; i < 1; i++) {
            assertEquals("rightMsb.bitValue(" + i + ", " + 1 + ")",
                    value, rightMsb.bitValue(i, 1));
            value *= 2;
        }
    }

    @Test
    public void testLIMS() {
        BitOrder leftMsb = BitOrder.LOW_INDEX_MOST_SIGNIFICANT;
        assertEquals("leftMsb.bitValue(3, 4)", 1, leftMsb.bitValue(3, 4));
        assertEquals("leftMsb.bitValue(2, 4)", 2, leftMsb.bitValue(2, 4));
        assertEquals("leftMsb.bitValue(1, 4)", 4, leftMsb.bitValue(1, 4));
        assertEquals("leftMsb.bitValue(0, 4)", 8, leftMsb.bitValue(0, 4));
        assertEquals("leftMsb.bitValue(0, 31)",
                0b1000000000000000000000000000000, leftMsb.bitValue(0, 31));
        assertEquals("leftMsb.bitValue(30, 31)",
                0b0000000000000000000000000000001, leftMsb.bitValue(30, 31));
        int value;
        value = 1;
        for (int i = 8 - 1; i >= 0; i--) {
            assertEquals("leftMsb.bitValue(" + i + ", " + 8 + ")",
                    value, leftMsb.bitValue(i, 8));
            value *= 2;
        }
        value = 1;
        for (int i = 5 - 1; i >= 0; i--) {
            assertEquals("leftMsb.bitValue(" + i + ", " + 5 + ")",
                    value, leftMsb.bitValue(i, 5));
            value *= 2;
        }
        value = 1;
        for (int i = 1 - 1; i >= 0; i--) {
            assertEquals("leftMsb.bitValue(" + i + ", " + 1 + ")",
                    value, leftMsb.bitValue(i, 1));
            value *= 2;
        }
    }

    @Test
    public void testLILSIndexException() {
        BitOrder order = BitOrder.LOW_INDEX_LEAST_SIGNIFICANT;
        indexExceptionHelper(order);
    }

    @Test
    public void testLIMSIndexException() {
        BitOrder order = BitOrder.LOW_INDEX_MOST_SIGNIFICANT;
        indexExceptionHelper(order);
    }

    @Test
    public void testLILSSizeException() {
        BitOrder order = BitOrder.LOW_INDEX_LEAST_SIGNIFICANT;
        sizeExceptionHelper(order);
    }

    @Test
    public void testLIMSSizeException() {
        BitOrder order = BitOrder.LOW_INDEX_MOST_SIGNIFICANT;
        sizeExceptionHelper(order);
    }
    
    private void sizeExceptionHelper(BitOrder order) {
        try {
            order.bitValue(0, -1);
            fail();
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertExceptionMessageContains(ex, "size", "-1", "1 to 31");
        }
        try {
            order.bitValue(0, 0);
            fail();
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertExceptionMessageContains(ex, "size", "0", "1 to 31");
        }
        try {
            order.bitValue(0, 32);
            fail();
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertExceptionMessageContains(ex, "size", "32", "1 to 31");
        }
        try {
            order.bitValue(0, 33);
            fail();
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertExceptionMessageContains(ex, "size", "33", "1 to 31");
        }
    }
    
    private void indexExceptionHelper(BitOrder order) {
        try {
            order.bitValue(-2, 8);
            fail();
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertExceptionMessageContains(ex, "index", "-2", "0 to 7");
        }
        try {
            order.bitValue(-1, 8);
            fail();
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertExceptionMessageContains(ex, "index", "-1", "0 to 7");
        }
        try {
            order.bitValue(8, 8);
            fail();
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertExceptionMessageContains(ex, "index", "8", "0 to 7");
        }
        try {
            order.bitValue(9, 8);
            fail();
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertExceptionMessageContains(ex, "index", "9", "0 to 7");
        }
        try {
            order.bitValue(9, 6);
            fail();
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertExceptionMessageContains(ex, "index", "9", "0 to 5");
        }
    }

}
