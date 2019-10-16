package org.ezvolve.core.test;

import static org.junit.Assert.*;

public final class ExceptionTestUtils {

    private ExceptionTestUtils() {
        throw new AssertionError(
                "Constructor invoked on utility class ExceptionTestUtils!");
    }

    public static void assertExceptionMessageContains(Exception ex,
                                                      Object... strings) {
        assertNotNull("exception was null", ex);
        String message = ex.getMessage();
        assertNotNull("message was null", message);
        if (strings != null) {
            for (Object obj : strings) {
                if (!message.contains(obj.toString())) {
                    fail("Exception message \"" + ex.getMessage() +
                            "\" does not contain expected string \"" +
                            obj + "\"");
                }
            }
        }
    }

}
