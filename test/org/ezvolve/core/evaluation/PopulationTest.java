/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ezvolve.core.evaluation;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.ezvolve.core.test.ExceptionTestUtils.*;

public class PopulationTest {

    @Test
    public void testFactory() {
        Population<Object> objects 
                = Population.of(new ArrayList<>(0));
        Population<Integer> integers 
                = Population.of(new ArrayList<Integer>(0));
        Population<String> strings 
                = Population.of(new ArrayList<String>(0));
        assertNotNull(objects);
        assertNotNull(integers);
        assertNotNull(strings);
    }
    
    @Test
    public void testToList() {
        List<String> input = new ArrayList<>(3);
        input.add("x");
        input.add("a");
        input.add("f");
        Population<String> population = Population.of(input);
        List<String> output = population.toList();
        assertNotSame(input, output);
        assertEquals(input.size(), output.size());
        for (int i = 0; i < input.size(); i++) {
            assertSame(input.get(i), output.get(i));
        }
    }
    
    @Test
    public void testEncapsulation() {
        List<String> input = new ArrayList<>(1);
        Population<String> population = Population.of(input);
        input.add("extra");
        List<String> output = population.toList();
        assertEquals(input.size() - 1, output.size());
    }
    
    @Test
    public void testIterator() {
        List<String> input = new ArrayList<>(3);
        input.add("1");
        input.add("9");
        input.add("5");
        Population<String> population = Population.of(input);
        List<String> temp = new ArrayList<>(3);
        for (String c : population) {
            temp.add(c);
        }
        assertEquals(3, temp.size());
        assertEquals("1", temp.remove(0));
        assertEquals("9", temp.remove(0));
        assertEquals("5", temp.remove(0));
    }
    
    @Test(expected=UnsupportedOperationException.class)
    public void testIteratorRemoveUnsupported() {
        List<String> input = new ArrayList<>(1);
        input.add("1");
        Population<String> population = Population.of(input);
        population.iterator().remove();
    }
    
    @Test
    public void testNullListException() {
        try {
            Population.of(null);
            fail();
        } catch (NullPointerException expected) {
            assertExceptionMessageContains(expected, "candidates");
        }
    }
    
    @Test
    public void testNullListElementException() {
        List<Object> list = new ArrayList<>(4);
        list.add(new Object());
        list.add(new Object());
        list.add(null);
        list.add(new Object());
        try {
            Population.of(list);
            fail();
        } catch (NullPointerException expected) {
            assertExceptionMessageContains(expected, "candidates", "2");
        }
    }
    
    @Test
    public void testGetCandidate() {
        List<String> list = new ArrayList<>(3);
        list.add("1");
        list.add("9");
        list.add("5");
        Population<String> population = Population.of(list);
        assertEquals("1", population.getCandidate(0));
        assertEquals("9", population.getCandidate(1));
        assertEquals("5", population.getCandidate(2));
    }
    
    @Test
    public void testGetCandidateBounds() {
        List<String> list = new ArrayList<>(3);
        list.add("1");
        list.add("9");
        list.add("5");
        Population<String> population = Population.of(list);
        try {
            population.getCandidate(-1);
            fail();
        } catch (IndexOutOfBoundsException expected) {
            assertExceptionMessageContains(expected, "index", "-1", "0 to 2");
        }
        try {
            population.getCandidate(3);
            fail();
        } catch (IndexOutOfBoundsException expected) {
            assertExceptionMessageContains(expected, "index", "3", "0 to 2");
        }
    }
    
    @Test
    public void testSize() {
        List<String> list = new ArrayList<>(0);
        assertEquals(0, Population.of(list).size());
        list.add("x");
        assertEquals(1, Population.of(list).size());
        list.add("x");
        assertEquals(2, Population.of(list).size());
    }
    
}
