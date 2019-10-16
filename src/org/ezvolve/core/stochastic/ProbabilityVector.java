package org.ezvolve.core.stochastic;

import java.util.Arrays;
import java.util.Random;
import org.ezvolve.core.encoding.BitString;

public final class ProbabilityVector
        implements CandidateSource<BitString> {
    
    private final Probability[] probabilities;
    
    private ProbabilityVector(Probability[] probabilities) {
        this.probabilities = probabilities;
    }
    
    public static ProbabilityVector of() {
        return new ProbabilityVector(new Probability[0]);
    }
    
    public static ProbabilityVector of(Probability... probabilities) {
        if (probabilities == null) {
            throw new NullPointerException("probabilities");
        }
        Probability[] clone = probabilities.clone();
        for (int i = 0; i < clone.length; i++) {
            if (clone[i] == null) {
                throw new NullPointerException("probabilities[" + i + "]");
            }
        }
        return new ProbabilityVector(clone);
    }
    
    public static ProbabilityVector of(double... doubleValues) {
        // TODO null
        Probability[] probabilities = new Probability[doubleValues.length];
        for (int i = 0; i < doubleValues.length; i++) {
            try {
                probabilities[i] = Probability.of(doubleValues[i]);
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException(
                        ex.getMessage() + " at index " + i);
            }
        }
        return new ProbabilityVector(probabilities);
    }
    
    public int length() {
        return probabilities.length;
    }
    
    public Probability probability(int index) {
        if (index < 0 || index >= probabilities.length) {
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index + 
                    ", expected: 0 to " + (probabilities.length-1));
        }
        return probabilities[index];
    }
    
    public double doubleValue(int index) {
        return probability(index).doubleValue();
    }
    
    public Probability[] toArray() {
        return probabilities; // FIXME
    }
    
    @Override
    public String toString() {
        StringBuilder rv = new StringBuilder(4 * probabilities.length + 3);
        rv.append("P[");
        for (int i = 0; i < probabilities.length; i++) {
            if (i != 0) {
                rv.append(", ");
            }
            rv.append(Double.toString((probabilities[i].doubleValue())));
        }
        rv.append(']');
        return rv.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof ProbabilityVector)) {
            return false;
        } else {
            ProbabilityVector other = (ProbabilityVector) o;
            if (other.length() != length()) {
                return false;
            } else {
                for (int i = 0; i < length(); i++) {
                    if (!probabilities[i].equals(other.probabilities[i])) {
                        return false;
                    }
                }
                return true;
            }
        }
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(probabilities);
    }

    public boolean nextBoolean(int index, Random random) {
        if (index < 0 || index >= probabilities.length) {
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index + 
                    ", expected: 0 to " + (probabilities.length-1));
        }
        if (random == null) {
            throw new NullPointerException("random");
        }
        return probabilities[index].nextBoolean(random);
    }

    @Override
    public BitString sample(Random random) {
        if (random == null) {
            throw new NullPointerException("random");
        }
        boolean[] bits = new boolean[length()];
        for (int i = 0; i < length(); i++) {
            bits[i] = probabilities[i].nextBoolean(random);
        }
        return BitString.of(bits);
    }
    
}
