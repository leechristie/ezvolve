/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ezvolve.core.util;

import java.util.Arrays;
import org.ezvolve.core.encoding.BitString;
import org.ezvolve.core.stochastic.CandidateSource;
import org.ezvolve.core.stochastic.Probability;
import org.ezvolve.core.stochastic.ProbabilityVector;

/**
 *
 * @author Lee
 */
public final class BitStrings {
    
    private BitStrings() {
        throw new AssertionError("BitStrings constructor invoked.");
    }
    
    public static CandidateSource<BitString>
            uniformDistribution(int length) {
        // TODO: optimise BitStrings.uniformDistribution
        // A small anonymous inner class could be written to make
        // this more optimised in a future verson
        if (length < 0) {
            throw new IllegalArgumentException(
                    "length = " + length + ", expected >= 0");
        }
        Probability[] arr = new Probability[length];
        Arrays.fill(arr, Probability.of(0.5));
        return ProbabilityVector.of(arr);
    }
    
}
