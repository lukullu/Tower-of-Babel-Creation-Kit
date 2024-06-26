package com.tbck.math;

public class MyMath {

    public static boolean isEven(int in) { return (in % 2) == 0; }

    public static boolean lineSegmentIntersection(Vec2 p1, Vec2 p2, Vec2 p3, Vec2 p4)
    {
        double h =  (p4.x - p3.x) * (p1.y - p2.y)  - (p1.x - p2.x) * (p4.y - p3.y);
        double t = ((p1.x - p3.x) * (p3.y - p4.y) - (p1.y - p3.y) * (p3.x - p4.x)) / h;
        double u = ((p1.x - p2.x) * (p1.y - p3.y) - (p1.y - p2.y) * (p3.x - p4.x)) / h;

        return t >= 0 && t <= 1 && u >= 0 && u <= 1;
    }

    public static Vec2 lineIntersection(Vec2 p1_1, Vec2 p1_2, Vec2 p2_1, Vec2 p2_2)
    {
        double d = (p1_1.x - p1_2.x) * (p2_1.y - p2_2.y) - (p1_1.y - p1_2.y) * (p2_1.x - p2_2.x);
        if (d == 0) { return null; }
        double xi = ((p2_1.x - p2_2.x) * (p1_1.x * p1_2.y - p1_1.y * p1_2.x) - (p1_1.x - p1_2.x) * (p2_1.x * p2_2.y - p2_1.y * p2_2.x)) / d;
        double yi = ((p2_1.y - p2_2.y) * (p1_1.x * p1_2.y - p1_1.y * p1_2.x) - (p1_1.y - p1_2.y) * (p2_1.x * p2_2.y - p2_1.y * p2_2.x)) / d;
        return new Vec2(xi, yi);
    }

    public static boolean isParallel(Vec2 p1_1, Vec2 p1_2, Vec2 p2_1, Vec2 p2_2)
    {
        if(p1_1.equals(p1_2) || p2_1.equals(p2_2))
            return false;

        Vec2 p1 = p1_1.subtract(p1_2);
        Vec2 p2 = p2_1.subtract(p2_2);

        return p1.x/p1.y == p2.x/p2.y;
    }

    public static double pythagoras(Vec2 input)
    {
        return Math.sqrt(input.x * input.x + input.y * input.y);
    }

    public static double pythagorasSqr(Vec2 input)
    {
        return input.x*input.x + input.y* input.y;
    }


}
