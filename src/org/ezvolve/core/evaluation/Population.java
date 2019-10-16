package org.ezvolve.core.evaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Population<T>
        implements Iterable<T> {
    
    private final List<T> candidates;

    private Population(List<T> candidates) {
        this.candidates = Collections.unmodifiableList(candidates);
    }
    
    public static <T> Population<T> of(List<T> candidates) {
        if (candidates == null) {
            throw new NullPointerException("candidates");
        }
        List<T> copy = new ArrayList<>(candidates.size());
        int i = 0;
        for (T c : candidates) {
            if (c == null) {
                throw new NullPointerException("candidates[" + i + "]");
            }
            copy.add(c);
            i++;
        }
        return new Population<>(copy);
    }
    
    public List<T> toList() {
        List<T> copy = new ArrayList<>(candidates.size());
        copy.addAll(candidates);
        return copy;
    }

    @Override
    public Iterator<T> iterator() {
        return candidates.iterator();
    }
    
    public T getCandidate(int index) {
        if (index < 0 || index >= candidates.size()) {
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index + 
                    ", expected: 0 to " + (candidates.size()-1));
        }
        return candidates.get(index);
    }
    
    public int size() {
        return candidates.size();
    }
    
}
