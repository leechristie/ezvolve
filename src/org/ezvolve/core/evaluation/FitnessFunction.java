package org.ezvolve.core.evaluation;

public interface FitnessFunction<C> {

    public Fitness evaluate(C candidate);
    
}
