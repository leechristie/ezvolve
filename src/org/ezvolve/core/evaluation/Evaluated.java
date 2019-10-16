package org.ezvolve.core.evaluation;

/**
 * <p>An evaluated candidate. A reference to the candidate is stored along
 * with its fitness.</p>
 *
 * @param <E> the encoding, must be an immutable value type
 * @author Lee Christie
 */
public final class Evaluated<E> {

    private final E candidate;
    private final Fitness fitness;

    private Evaluated(E candidate, Fitness fitness) {
        this.candidate = candidate;
        this.fitness = fitness;
    }

    /**
     * Creates an evaluated candidate with the specified candidate and fitness.
     *
     * @param <E> the encoding, must be an immutable value type
     * @param candidate the candidate solution, not {@code null}
     * @param fitness the fitness of candidate, not {@code null}
     * @return the individual, never {@code null}
     * @throws NullPointerException if either argument is {@code null}
     */
    public static <E> Evaluated<E> of(E candidate, Fitness fitness) {
        if (candidate == null) {
            throw new NullPointerException("candidate");
        }
        if (fitness == null) {
            throw new NullPointerException("fitness");
        }
        return new Evaluated<>(candidate, fitness);
    }

    /**
     * Returns the candidate solution.
     *
     * @return the candidate solution, never {@code null}
     */
    public E candidate() {
        return candidate;
    }

    /**
     * Returns the fitness.
     *
     * @return the fitness, never {@code null}
     */
    public Fitness fitness() {
        return fitness;
    }

    /**
     * Compares the specified object with this evaluated candidate for equality.
     * Returns {@code true} if and only if the specified object represents both
     * an equal candidate, and an equal fitness to this evaluated candidate.
     * This method conforms to the general contract of
     * {@link java.lang.Object#equals(java.lang.Object)} and is consistent with
     * {@link #hashCode()}.
     *
     * @param o the object to be compared for equality with this evaluated
     * candidate
     * @return {@code true} if the specified object is equal to this evaluated
     * candidate
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Evaluated<?>)) {
            return false;
        }
        final Evaluated<?> other = (Evaluated<?>) o;
        if (!(other.fitness().equals(this.fitness()))) {
            return false;
        }
        if (!(other.candidate().equals(this.candidate()))) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code for this evaluated candidate. The hash code algorithm
     * is unspecified and hence may be subject to change between versions of the
     * API. This method conforms to the general contract of
     * {@link java.lang.Object#hashCode()} and is consistent with
     * {@link #equals(java.lang.Object)}.
     *
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + candidate().hashCode();
        result = 31 * result + fitness().hashCode();
        return result;
    }

    /**
     * Returns a string representation of the evaluated candidate.
     *
     * @return a string representation of the evaluated candidate,
     * not {@code null}
     */
    @Override
    public String toString() {
        return "[candidate=" + candidate
                + ",fitness=" + fitness + "]";
    }
    
}
