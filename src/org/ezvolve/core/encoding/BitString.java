package org.ezvolve.core.encoding;

import java.util.Arrays;

public final class BitString {
    
    private boolean[] bits;
    
    private BitString(boolean[] bits) {
        this.bits = bits;
    }
    
    public static BitString of() {
        return new BitString(new boolean[0]);
    }
    
    public static BitString of(boolean... bits) {
        if (bits == null) {
            throw new NullPointerException("bits");
        }
        return new BitString(bits.clone());
    }
    
    public static BitString of(int... bits) {
        if (bits == null) {
            throw new NullPointerException("bits");
        }
        boolean[] booleans = new boolean[bits.length];
        for (int i = 0; i < bits.length; i++) {
            switch (bits[i]) {
                case 0:
                    // default state = false
                    break;
                case 1:
                    booleans[i] = true;
                    break;
                default:
                    throw new IllegalArgumentException(
                            "Unexpected bit value: " + bits[i]
                            + " at index " + i);
            }
        }
        return new BitString(booleans);
    }
    
    public int length() {
        return bits.length;
    }
    
    public BitString withBit(int index, boolean bit) {
        if (isSet(index) == bit) {
            return this;
        } else {
            boolean[] temp = bits.clone();
            temp[index] = bit;
            return new BitString(temp);
        }
    }
    
    public boolean isSet(int index) {
        if (index < 0 || index >= bits.length) {
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index + 
                    ", expected: 0 to " + (bits.length-1));
        }
        return bits[index];
    }
    
    public int bit(int index) {
        return isSet(index) ? 1 : 0;
    }
    
    public int hammingDistanceTo(BitString other) {
        if (other == null) {
            throw new NullPointerException("other");
        } else if (other.length() != this.length()) {
            throw new IllegalArgumentException(
                    "length = " + other.length() + ", expected: " + length());
        } else {
            int numBitsDifferent = 0;
            for (int i = 0; i < length(); i++) {
                if (isSet(i) != other.isSet(i)) {
                    numBitsDifferent++;
                }
            }
            return numBitsDifferent;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof BitString)) {
            return false;
        } else {
            BitString other = (BitString) o;
            if (other.length() != length()) {
                return false;
            } else {
                for (int i = 0; i < length(); i++) {
                    if (isSet(i) != other.isSet(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(bits);
    }
    
    @Override
    public String toString() {
        StringBuilder rv = new StringBuilder(bits.length);
        for (boolean b : bits) {
            rv.append(b ? '1' : '0');
        }
        return rv.toString();
    }
    
}
