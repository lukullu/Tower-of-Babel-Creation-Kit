package com.tbck.math;

public class MyMath {

    public static boolean isEven(int in) { return (in % 2) == 0; }

    public static Vec2 lineSegmentIntersection(Vec2 line_r1s, Vec2 line_r1e, Vec2 line_r2s, Vec2 line_r2e)
    {
        double h = (line_r2e.x - line_r2s.x) * (line_r1s.y - line_r1e.y) - (line_r1s.x - line_r1e.x) * (line_r2e.y - line_r2s.y);
        double t1 = ((line_r2s.y - line_r2e.y) * (line_r1s.x - line_r2s.x) + (line_r2e.x - line_r2s.x) * (line_r1s.y - line_r2s.y)) / h;
        double t2 = ((line_r1s.y - line_r1e.y) * (line_r1s.x - line_r2s.x) + (line_r1e.x - line_r1s.x) * (line_r1s.y - line_r2s.y)) / h;

        if(t1 >= 0 && t1 <= 1 && t2 >= 0 && t2 <= 1)
        {
            return new Vec2(line_r2e.x + t1 * (line_r2s.x - line_r2e.x), line_r2e.y + t1 * (line_r2s.y - line_r2e.y));
        }

        return null;
    }

    public static Vec2 lineIntersection(Vec2 p1_1, Vec2 p1_2, Vec2 p2_1, Vec2 p2_2)
    {
        double d = (p1_1.x - p1_2.x) * (p2_1.y - p2_2.y) - (p1_1.y - p1_2.y) * (p2_1.x - p2_2.x);
        if (d == 0) { return null; }
        double xi = ((p2_1.x - p2_2.x) * (p1_1.x * p1_2.y - p1_1.y * p1_2.x) - (p1_1.x - p1_2.x) * (p2_1.x * p2_2.y - p2_1.y * p2_2.x)) / d;
        double yi = ((p2_1.y - p2_2.y) * (p1_1.x * p1_2.y - p1_1.y * p1_2.x) - (p1_1.y - p1_2.y) * (p2_1.x * p2_2.y - p2_1.y * p2_2.x)) / d;
        return new Vec2(xi, yi);
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
