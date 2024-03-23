package com.lukullu.tbck.utils;

import java.util.ArrayList;

public class Triangle
{

    public Vec2 v1;
    public Vec2 v2;
    public Vec2 v3;

    public boolean enabled = true;

    public Triangle(Vec2 v1, Vec2 v2, Vec2 v3)
    {
        this.v1 = v1; this.v2 = v2; this.v3 = v3;
    }

    public void updatePos(Vec2 delta)
    {
        this.v1 = v1.add(delta);
        this.v2 = v2.add(delta);
        this.v3 = v3.add(delta);
    }

    public void updateRot(Vec2 origin ,double delta)
    {
        v1 = v1.rotate(origin,delta);
        v2 = v2.rotate(origin,delta);
        v3 = v3.rotate(origin,delta);
    }

    public ArrayList<Vec2> getVertices()
    {
        ArrayList<Vec2> out = new ArrayList<>();
        out.add(v1);
        out.add(v2);
        out.add(v3);
        return out;
    }

    public Vec2 getPosition()
    {
        return calcCenter();
    }

    private Vec2 calcCenter()
    {
        return v1.add(v2).add(v3).divide(3);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "v1=" + v1 +
                ", v2=" + v2 +
                ", v3=" + v3 +
                '}';
    }
}
