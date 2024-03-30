package com.lukullu.tbck.utils;

import java.util.Objects;

public class Vec2
{
    public static Vec2 ZERO_VECTOR2 = new Vec2(0,0);
    public double x, y;
    public Vec2(double x, double y){ this.x = x; this.y = y; }

    // Vector Arithmetic
    public Vec2 add(Vec2 other_vector)
    {
        return new Vec2(x + other_vector.x, y + other_vector.y);
    }
    public static Vec2 add(Vec2 v1, Vec2 v2) { return v1.add(v2); }
    public Vec2 subtract(Vec2 other_vector)
    {
        return new Vec2(x - other_vector.x, y - other_vector.y);
    }
    public Vec2 multiply(Vec2 other_vector)
    {
        return new Vec2(x * other_vector.x, y * other_vector.y);
    }
    public Vec2 multiply(double value) { return new Vec2(x * value, y * value); }
    public Vec2 divide(Vec2 other_vector)
    {
        return new Vec2(x / other_vector.x, y / other_vector.y);
    }
    public Vec2 divide(double value) { return new Vec2(x / value, y / value); }
    public Vec2 mod(Vec2 other_vector) { return new Vec2(x % other_vector.x, y % other_vector.y); }
    public Vec2 mod(double value) { return new Vec2(x % value, y % value); }
    public double length(){return Math.sqrt(Math.max(0, x*x + y*y)); }
    public Vec2 normalise(){ return this.divide(this.length()); }
    public static double dotProduct(Vec2 v1, Vec2 v2){ return v1.x * v2.x + v1.y * v2.y; }
    public Vec2 align(Vec2 other_vector){ return new Vec2 (Math.signum(other_vector.x) * Math.abs(x), Math.signum(other_vector.y) * Math.abs(y)); }
    public Vec2 normalVector() { return new Vec2(y,-x); }
    public Vec2 abs() { return new Vec2(Math.abs(x),Math.abs(y)); }
    public Vec2 rotate(Vec2 pivot, double angle)
    {
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        Vec2 relative = this.subtract(pivot);
        Vec2 newRelative = new Vec2(
                relative.x * c - relative.y * s,
                relative.x * s + relative.y * c
        );
        return newRelative.add(pivot);
    }

    @Override
    public String toString() {
        return "Vec2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vec2 vec2)) return false;
        return Double.compare(x, vec2.x) == 0 && Double.compare(y, vec2.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
