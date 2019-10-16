package org.ezvolve.core.stochastic;

import java.util.Random;

public interface CandidateSource<T> {

    public T sample(Random random);
    
}
