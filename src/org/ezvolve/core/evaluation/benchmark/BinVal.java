package org.ezvolve.core.evaluation.benchmark;

import org.ezvolve.core.util.BitOrder;
import org.ezvolve.core.encoding.BitString;
import org.ezvolve.core.evaluation.Fitness;
import org.ezvolve.core.evaluation.FitnessFunction;

public final class BinVal 
        implements FitnessFunction<BitString> {
    
    private static final FitnessFunction<BitString> LEAST_INSTANCE
            = new BinVal(BitOrder.LOW_INDEX_LEAST_SIGNIFICANT);
    
    private static final FitnessFunction<BitString> MOST_INSTANCE
            = new BinVal(BitOrder.LOW_INDEX_MOST_SIGNIFICANT);
    
    private final BitOrder bitOrder;
    
    private BinVal(BitOrder bitOrder) {
        this.bitOrder = bitOrder;
    }

    public static FitnessFunction<BitString> getInstance(BitOrder bitOrder) {
        if (bitOrder == null) {
            throw new NullPointerException("bitOrder");
        } else if (bitOrder == BitOrder.LOW_INDEX_LEAST_SIGNIFICANT) {
            return LEAST_INSTANCE;
        } else if (bitOrder == BitOrder.LOW_INDEX_MOST_SIGNIFICANT) {
            return MOST_INSTANCE;
        } else {
            return new BinVal(bitOrder); // branch not used
        }
    }

    @Override
    public Fitness evaluate(BitString candidate) {
        if (candidate == null) {
            throw new NullPointerException("candidate");
        }
        if (candidate.length() > 31) {
            throw new IllegalArgumentException("candidate.length() = "
                    + candidate.length() + ", expected <= 31");
        }
        int sum = 0;
        for (int i = 0; i < candidate.length(); i++) {
            if (candidate.isSet(i)) {
                sum += bitOrder.bitValue(i, candidate.length());
            }
        }
        return Fitness.of(sum);
    }
    
}
