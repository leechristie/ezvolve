package org.ezvolve.core.evaluation;

import java.util.Collection;
import java.util.Comparator;

/**
 * {{{{@TODO: update old javadoc in this file}}}}
 */

/**
 * Specifies a rule for comparing fitness values, {@literal i.e.} maximization
 * or minimization. {@code FitnessComparator} can be used to select the best
 * {@link org.ezvolve.core.evaluation.Fitness} or {@link org.ezvolve.EvaluatedCandidate} from
 * two, or from a collection. The values in this enumeration may also be used as
 * implementations of {@link java.util.Comparator} for sorting instances of
 * {@link org.ezvolve.core.evaluation.Fitness} in the order worst-first. {@code null} fitnesses
 * are always considered worse than non-null fitnesses.
 * <p>
 * To obtain an instance for sorting instances of
 * {@link org.ezvolve.core.evaluation.Evaluated}, use the
 * {@link #evaluatedComparator()} method, for example:
 * <p>
 * <code>Comparator&lt;Evaluated&lt;BitString&gt;&gt; comp =
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FitnessComparator.MAXIMIZATION.evaluatedComparator();</code>
 * 
 * @author Lee Christie
 */
public enum FitnessComparator
        implements Comparator<Fitness> {

    /**
     * Fitnesses of lower numeric values are considered better.
     */
    MINIMIZATION() {
        @Override
        public Fitness worstNonNullFitness() {
            return POSITIVE_INFINITY_FITNESS;
        }
        @Override
        public int compare(Fitness left, Fitness right) {
            if (left == null && right == null) {
                return 0;
            } else if (left == null) {
                return -1;
            } else if (right == null) {
                return 1;
            } else if (left.doubleValue() > right.doubleValue()) {
                return -1;
            } else if (left.doubleValue() < right.doubleValue()) {
                return 1;
            } else {
                return 0;
            }
        }
    },

    /**
     * Fitnesses of higher numeric values are considered better.
     */
    MAXIMIZATION() {
        @Override
        public Fitness worstNonNullFitness() {
            return NEGATIVE_INFINITY_FITNESS;
        }
        @Override
        public int compare(Fitness left, Fitness right) {
            if (left == null && right == null) {
                return 0;
            } else if (left == null) {
                return -1;
            } else if (right == null) {
                return 1;
            } else if (left.doubleValue() > right.doubleValue()) {
                return 1;
            } else if (left.doubleValue() < right.doubleValue()) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    private static final Fitness NEGATIVE_INFINITY_FITNESS
            = Fitness.of(Double.NEGATIVE_INFINITY);

    private static final Fitness POSITIVE_INFINITY_FITNESS
            = Fitness.of(Double.POSITIVE_INFINITY);

    private volatile Comparator<?> EC_COMP = null;

    /**
     * Returns the worst posable fitness value. This value is never considered
     * better than any other non-null fitness value.
     *
     * @return the worst posable non-null fitness
     */
    public abstract Fitness worstNonNullFitness();
    
    /**
     * Returns {@code -1}, {@code 0}, or {@code 1} by comparing the fitnesses,
     * see
     * {@link java.util.Comparator#compare(java.lang.Object, java.lang.Object)}
     * for details.
     *
     * @param left the first instance
     * @param right the second instance
     * @return {@code -1}, {@code 0}, or {@code 1}
     */
    @Override
    public abstract int compare(Fitness left, Fitness right);

    /**
     * Returns {@code -1}, {@code 0}, or {@code 1} by comparing the candidates,
     * see
     * {@link java.util.Comparator#compare(java.lang.Object, java.lang.Object)}
     * for details.
     *
     * @param <C> the solution type, must be an immutable value type
     * @param left the first instance
     * @param right the second instance
     * @return {@code -1}, {@code 0}, or {@code 1}
     */
    public <C> int compare(Evaluated<C> left,
                           Evaluated<C> right) {
        Fitness leftFitness = null;
        if (left != null) {
            leftFitness = left.fitness();
        }
        Fitness rightFitness = null;
        if (right != null) {
            rightFitness = right.fitness();
        }
        return compare(leftFitness, rightFitness);
    }

    /**
     * Returns a {@link java.util.Comparator} for sorting
     * {@link org.ezvolve.EvaluatedCandidate} instances.
     *
     * @param <C> the solution type, must be an immutable value type
     * @return a comparator
     */
    public <C> Comparator<Evaluated<C>>
            evaluatedComparator() {
        if (EC_COMP == null) {
            EC_COMP = new Comparator<Evaluated<C>>() {
                @Override
                public int compare(Evaluated<C> left,
                                   Evaluated<C> right) {
                    return FitnessComparator.this.compare(left, right);
                }
            };
        }
        // Allowing reuse of instance as the comparison *only* compares Fitness
        // values and not the C values
        @SuppressWarnings("unchecked") Comparator<Evaluated<C>> rv
                = (Comparator<Evaluated<C>>) EC_COMP;
        return rv;
    }

    /**
     * Returns the best of two specified fitnesses according to this comparator.
     * If either fitness is {@code null}, returns the other fitness. If both
     * fitnesses are {@code null}, returns {@code null}.
     *
     * @param fitness0 the first fitness
     * @param fitness1 the second fitness
     * @return the best fitness, or {@code null} if both are {@code null}
     */
    public Fitness bestOf(Fitness fitness0, Fitness fitness1) {
        if (fitness0 == null) {
            return fitness1;
        }
        if (fitness1 == null) {
            return fitness0;
        }
        int c = compare(fitness0, fitness1);
        if (c < 0) {
            return fitness1;
        } else {
            return fitness0;
        }
    }

    /**
     * Returns the best of two specified candidates according to this
     * comparator. If either candidate is {@code null}, returns the other
     * candidate. If both candidates are {@code null}, returns {@code null}.
     * 
     * @param <C> the solution type, must be an immutable value type
     * @param candidate0 the first candidate
     * @param candidate1 the second candidate
     * @return the best candidate, or {@code null} if both are null
     */
    public <C> Evaluated<C> bestOf(Evaluated<C> candidate0,
                                   Evaluated<C> candidate1) {
        if (candidate0 == null) {
            return candidate1;
        }
        if (candidate1 == null) {
            return candidate0;
        }
        int c = compare(candidate0, candidate1);
        if (c < 0) {
            return candidate1;
        } else {
            return candidate0;
        }
    }

    /**
     * Returns the best of a collection of candidates according to this
     * comparator. {@code null} elements are ignored, if the collection is empty
     * or contains only {@code null} elements, this method returns {@code null}.
     * 
     * @param <C> the solution type, must be an immutable value type
     * @param candidates the collection of candidates
     * @return the best candidate, or {@code null} if there are no non-null
     *         candidates
     */
    public <C> Evaluated<C> bestOf(
            Collection<Evaluated<C>> candidates) {
        Evaluated<C> best = null;
        for (Evaluated<C> current : candidates) {
            if (best == null) {
                best = current;
            } else {
                best = bestOf(best, current);
            }
        }
        return best;
    }

}