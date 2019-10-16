package org.ezvolve.core.util;

public enum BitOrder {

    LOW_INDEX_LEAST_SIGNIFICANT {
        @Override
        public int bitValue(int index, int size) {
            validateSize(size);
            validateIndex(index, size);
            return 1 << index;
        }
    },
    
    LOW_INDEX_MOST_SIGNIFICANT {
        @Override
        public int bitValue(int index, int size) {
            validateSize(size);
            validateIndex(index, size);
            return 1 << ((size - 1) - index);
        }
    };

    public abstract int bitValue(int index, int size);
    
    private static void validateIndex(int index, int size) {
        if (index < 0 || index > size - 1) {
            throw new IllegalArgumentException(
                    "index = " + index + ", expected: 0 to " + (size - 1));
        }
    }
    
    private static void validateSize(int size) {
        if (size < 1 || size > 31) {
            throw new IllegalArgumentException(
                    "size = " + size + ", expected: 1 to 31");
        }
    }
    
}
