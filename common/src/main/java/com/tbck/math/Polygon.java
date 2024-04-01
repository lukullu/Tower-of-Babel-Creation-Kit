package com.tbck.math;

import java.util.ArrayList;

public class Polygon
{

    public ArrayList<Vec2> vertices = new ArrayList<>();

    public Polygon(ArrayList<Vec2> vertices)
    {
        this.vertices = vertices;
    }

    public ArrayList<Vec2> getVertices(){ return vertices; }
    public Vec2 getPosition() { return calcPos(); }


    private Vec2 calcPos()
    {
        Vec2 acc = Vec2.ZERO_VECTOR2;
        int counter = 0;
        for(Vec2 vertex : vertices)
        {
            acc = acc.add(vertex);
            counter++;
        }
        return acc.divide(counter);
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "vertices=" + vertices.stream().map(Vec2::toString) +
                '}';
    }
}
