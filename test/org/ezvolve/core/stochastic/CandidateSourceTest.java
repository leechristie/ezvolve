package org.ezvolve.core.stochastic;

import java.util.Random;
import org.junit.Test;

public class CandidateSourceTest {

    @Test
    public void testInterfaceExists() {
        CandidateSource<Object> candidateSource;
    }
    
    @Test(expected=NullPointerException.class)
    public void testSampleMethodExists() {
        CandidateSource<Object> candidateSource = null;
        candidateSource.sample(new Random());
    }
    
    @Test(expected=NullPointerException.class)
    public void testSampleMethodReturnType() {
        CandidateSource<String> stringSource = null;
        String string = stringSource.sample(new Random());
        CandidateSource<Integer> integerSource = null;
        Integer integer = integerSource.sample(new Random());
    }
    
    @Test(expected=UnsupportedOperationException.class)
    public void testImplements() {
        CandidateSource<Integer> intDummy = new Dummy<>();
        int integer = intDummy.sample(new Random());
        CandidateSource<String> stringDummy = new Dummy<>();
        String string = stringDummy.sample(new Random());
    }
    
    private class Dummy<T> implements CandidateSource<T> {
        @Override
        public T sample(Random random) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
}
