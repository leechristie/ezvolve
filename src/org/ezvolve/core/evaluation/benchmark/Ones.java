package org.ezvolve.core.evaluation.benchmark;

import org.ezvolve.core.encoding.BitString;
import org.ezvolve.core.evaluation.Fitness;
import org.ezvolve.core.evaluation.FitnessFunction;

public final class Ones
        implements FitnessFunction<BitString> {

    private static FitnessFunction<BitString> INSTANCE = new Ones();

    private Ones() {}

    public static FitnessFunction<BitString> getInstance() {
        return INSTANCE;
    }

    @Override
    public Fitness evaluate(BitString candidate) {
        if (candidate == null) {
            throw new NullPointerException("candidate");
        }
        int sum = 0;
        for (int i = 0; i < candidate.length(); i++) {
            sum += candidate.bit(i);
        }
        return Fitness.of(sum);
    }

}
