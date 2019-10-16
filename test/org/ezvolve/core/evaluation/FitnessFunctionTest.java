package org.ezvolve.core.evaluation;

import org.junit.Test;

public class FitnessFunctionTest {

    @Test
    public void testInterfaceExists() {
        FitnessFunction<Object> fitnessFunction;
    }
    
    @Test(expected=NullPointerException.class)
    public void testEvaluateMethodExists() {
        FitnessFunction<Integer> intFunction = null;
        intFunction.evaluate(0);
        FitnessFunction<String> stringFunction = null;
        stringFunction.evaluate("");
    }
    
    @Test(expected=NullPointerException.class)
    public void testEvaluateMethodReturnType() {
        FitnessFunction<Integer> fitnessFunction = null;
        Fitness fitness = fitnessFunction.evaluate(0);
    }
    
    @Test(expected=UnsupportedOperationException.class)
    public void testImplements() {
        FitnessFunction<Integer> intDummy = new Dummy<>();
        intDummy.evaluate(0);
        FitnessFunction<String> stringDummy = new Dummy<>();
        stringDummy.evaluate("");
    }
    
    private class Dummy<C> implements FitnessFunction<C> {
        @Override
        public Fitness evaluate(C candidate) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
}
