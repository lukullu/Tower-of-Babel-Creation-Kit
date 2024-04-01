package com.tbck.math;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Polygon implements Serializable
{
    
    @Serial private static final long serialVersionUID = 3756687872714555217L;
    
    public ArrayList<Vec2> vertices = new ArrayList<>();

    public Polygon(java.util.List<Vec2> vertices) { this(new ArrayList<>(vertices)); }
    
    public Polygon(ArrayList<Vec2> vertices)
    {
        this.vertices = vertices;
    }
    
    public Polygon scale(double scale) {
        return new Polygon(new ArrayList<>(
                vertices.stream().map(vec -> vec.multiply(scale)).toList()
        ));
    }
    
    public java.awt.Polygon asNative() {
        int[] xPoints = new int[vertices.size()];
        int[] yPoints = new int[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            xPoints[i] = (int) vertices.get(i).x;
            yPoints[i] = (int) vertices.get(i).y;
        }
        return new java.awt.Polygon(xPoints, yPoints, vertices.size());
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

    public String toString() {
        return vertices.toString();
    }
}
