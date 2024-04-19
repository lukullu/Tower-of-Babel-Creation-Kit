package com.tbck.math;

import javax.swing.text.Utilities;

@SuppressWarnings("serial")
public class UInt32 extends Number implements Comparable<UInt32> {

    public static final long MAX_VALUE = 4294967295L;
    public static final long MIN_VALUE = 0;
    private long value;

    public UInt32(long value) {
        this.value = value;
    }

    public UInt32(String value) {
        this(Long.parseLong(value));
    }

    public UInt32 add(UInt32 a)
    {
        return new UInt32(a.longValue() + this.longValue());
    }

    @Override
    public byte byteValue() {
        return (byte) value;
    }

    public int compareTo(UInt32 other) {
        return (int) (value - other.value);
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof UInt32) && (((UInt32) o).value == value);
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return (int) value;
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return /* (long) */value;
    }

    @Override
    public short shortValue() {
        return (short) value;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}