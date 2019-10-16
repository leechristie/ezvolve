package org.ezvolve.core.stochastic;

import java.util.Random;

/**
 * <p>Represents a probability in the range 0 to 1 (both inclusive). This may be
 * used to generate a boolean with {@code this} probability.</p>
 *
 * @author Lee Christie
 */
public final class Probability {

    private final double doubleValue;

    private Probability(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    /**
     * Returns the probability as a {@code double}.
     *
     * @return the probability as a {@code double}
     */
    public double doubleValue() {
        return doubleValue;
    }

    /**
     * Returns a {@code boolean} which has {@code this} probability, of being
     * {@code true}.
     *
     * @param random the radom number generator, not {@code null}
     * @return a random {@code boolean}
     * @throws NullPointerException if {@code random} is {@code null}
     */
    public boolean nextBoolean(Random random) {
        if (random == null) {
            throw new NullPointerException("random");
        }
        return doubleValue > random.nextDouble();
    }

    /**
     * Returns an instance of {@code Probability} from the specified double.
     *
     * @param doubleValue the probability as a double, between 0.0 and 1.0
     *                    (both inclusive)
     * @return an instance of {@code Probability}
     * @throws IllegalArgumentException if {@code doubleValue} is {@code < 0.0}
     *         or {@code > 1.0}
     */
    public static Probability of(double doubleValue) {
        if (Double.isNaN(doubleValue) ||
                doubleValue < 0.0 || doubleValue > 1.0) {
            throw new IllegalArgumentException(
                    "doubleValue = " + doubleValue + ", expected: [0.0, 1.0]");
        }
        return new Probability(doubleValue);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Probability)) {
            return false;
        }
        final Probability other = (Probability) o;
        if (other.doubleValue != doubleValue) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        final long longValue = Double.doubleToLongBits(doubleValue);
        int result = 17;
        result = 31 * result + (int) (longValue ^ (longValue >>> 32));
        return result;
    }

    /**
     * Returns a string representation of the probability.
     *
     * @return a string representation of the probability, not {@code null}
     */
    @Override
    public String toString() {
        return "P(" + Double.toString(doubleValue) + ")";
    }

}
